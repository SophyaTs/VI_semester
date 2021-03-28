#include<stdio.h>
#include<stdlib.h>
#include <mpi.h>
#include<math.h>
#include <time.h>
#include <sys/time.h>
#include <chrono>

int allocMatrix(int*** mat, int rows, int cols) {
	// Allocate rows*cols contiguous items
	int* p = (int*)malloc(sizeof(int*) * rows * cols);
	if (!p) {
		return -1;
	}
	// Allocate row pointers
	*mat = (int**)malloc(rows * sizeof(int*));
	if (!mat) {
		free(p);
		return -1;
	}

	// Set up the pointers into the contiguous memory
	for (int i = 0; i < rows; i++) {
		(*mat)[i] = &(p[i * cols]);
	}
	return 0;
}

int freeMatrix(int ***mat) {
	free(&((*mat)[0][0]));
	free(*mat);
	return 0;
}

void matrixMultiply(int **a, int **b, int rows, int cols, int ***c) {
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			int val = 0;
			for (int k = 0; k < rows; k++) {
				val += a[i][k] * b[k][j];
 			}
			(*c)[i][j] = val;
		}
	}
}

void printMatrix(int **mat, int size) {
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			printf("%d ", mat[i][j]);
		}
		printf("\n");
	}
}

int main(int argc, char* argv[]) {
	MPI_Comm cartComm;
	int dim[2], period[2], reorder;
	int coord[2], id;
	int **A = NULL, **B = NULL, **C = NULL;
	int **localA = NULL, **localB = NULL, **localC = NULL;
	int **localARec = NULL, **localBRec = NULL;
	int rows = 0;
	int columns;
	int count = 0;
	int worldSize;
	int procDim;
	int blockDim;
	int left, right, up, down;
	int bCastData[4];

	// Initialize the MPI environment
	MPI_Init(&argc, &argv);

	// World size
	MPI_Comm_size(MPI_COMM_WORLD, &worldSize);
	
	// Get the rank of the process
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	std::chrono::steady_clock::time_point begin = std::chrono::steady_clock::now();
	if (rank == 0) {
		int n;
		char ch;

		columns = atoi(argv[1]);
		rows = columns;

		// Check matrix and world size
		if (columns != rows) {
			printf("[ERROR] Matrix must be square!\n");
			MPI_Abort(MPI_COMM_WORLD, 2);
		}
		double sqroot = sqrt(worldSize);
		if ((sqroot - floor(sqroot)) != 0) {
			printf("[ERROR] Number of processes must be a perfect square!\n");
			MPI_Abort(MPI_COMM_WORLD, 2);
		}
		int intRoot = (int)sqroot;
		if (columns%intRoot != 0 || rows%intRoot != 0) {
			printf("[ERROR] Number of rows/columns not divisible by %d!\n", intRoot);
			MPI_Abort(MPI_COMM_WORLD, 3);
		}
		procDim = intRoot;
		blockDim = columns / intRoot;

		if (allocMatrix(&A, rows, columns) != 0) {
			printf("[ERROR] Matrix alloc for A failed!\n");
			MPI_Abort(MPI_COMM_WORLD, 4);
		}
		if (allocMatrix(&B, rows, columns) != 0) {
			printf("[ERROR] Matrix alloc for B failed!\n");
			MPI_Abort(MPI_COMM_WORLD, 5);
		}

		// generate matrix A
		srand ( time(NULL) );
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				A[i][j] = rand()%10;
			}
		}
		//*******************************************************************printf("A matrix:\n");
		//*******************************************************************printMatrix(A, rows);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				B[i][j] = rand()%10;
			}
		}
		//*******************************************************************printf("B matrix:\n");
		//*******************************************************************printMatrix(B, rows);

		if (allocMatrix(&C, rows, columns) != 0) {
			printf("[ERROR] Matrix alloc for C failed!\n");
			MPI_Abort(MPI_COMM_WORLD, 6);
		}

		bCastData[0] = procDim;
		bCastData[1] = blockDim;
		bCastData[2] = rows;
		bCastData[3] = columns;
	}
	

	// Create 2D Cartesian grid of processes
	MPI_Bcast(&bCastData, 4, MPI_INT, 0, MPI_COMM_WORLD);
	procDim = bCastData[0];
	blockDim = bCastData[1];
	rows = bCastData[2];
	columns = bCastData[3];

	dim[0] = procDim; dim[1] = procDim;
	period[0] = 1; period[1] = 1;
	reorder = 1;
	MPI_Cart_create(MPI_COMM_WORLD, 2, dim, period, reorder, &cartComm);

	// Allocate local blocks for A and B
	allocMatrix(&localA, blockDim, blockDim);
	allocMatrix(&localB, blockDim, blockDim);

	// Create datatype to describe the subarrays of the global array
	int globalSize[2] = { rows, columns };
	int localSize[2] = { blockDim, blockDim };
	int starts[2] = { 0,0 };
	MPI_Datatype type, subarrtype;
	MPI_Type_create_subarray(2, globalSize, localSize, starts, MPI_ORDER_C, MPI_INT, &type);
	MPI_Type_create_resized(type, 0, blockDim * sizeof(int), &subarrtype);
	MPI_Type_commit(&subarrtype);

	int *globalptrA = NULL;
	int *globalptrB = NULL;
	int *globalptrC = NULL;
	if (rank == 0) {
		globalptrA = &(A[0][0]);
		globalptrB = &(B[0][0]);
		globalptrC = &(C[0][0]);
	}

	// Scatter the array to all processors
	int* sendCounts = (int*)malloc(sizeof(int) * worldSize);
	int* displacements = (int*)malloc(sizeof(int) * worldSize);

	if (rank == 0) {
		for (int i = 0; i < worldSize; i++) {
			sendCounts[i] = 1;
		}
		int disp = 0;
		for (int i = 0; i < procDim; i++) {
			for (int j = 0; j < procDim; j++) {
				displacements[i * procDim + j] = disp;
				disp += 1;
			}
			disp += (blockDim - 1)* procDim;
		}
	}

	MPI_Scatterv(globalptrA, sendCounts, displacements, subarrtype, &(localA[0][0]),
		rows * columns / (worldSize), MPI_INT,
		0, MPI_COMM_WORLD);
	MPI_Scatterv(globalptrB, sendCounts, displacements, subarrtype, &(localB[0][0]),
		rows * columns / (worldSize), MPI_INT,
		0, MPI_COMM_WORLD);

	if (allocMatrix(&localC, blockDim, blockDim) != 0) {
		printf("[ERROR] Matrix alloc for localC in rank %d failed!\n", rank);
		MPI_Abort(MPI_COMM_WORLD, 7);
	}

	// Initial skew
	MPI_Cart_coords(cartComm, rank, 2, coord);
	MPI_Cart_shift(cartComm, 1, coord[0], &left, &right);
	MPI_Sendrecv_replace(&(localA[0][0]), blockDim * blockDim, MPI_INT, left, 1, right, 1, cartComm, MPI_STATUS_IGNORE);
	MPI_Cart_shift(cartComm, 0, coord[1], &up, &down);
	MPI_Sendrecv_replace(&(localB[0][0]), blockDim * blockDim, MPI_INT, up, 1, down, 1, cartComm, MPI_STATUS_IGNORE);

	// Init C
	for (int i = 0; i < blockDim; i++) {
		for (int j = 0; j < blockDim; j++) {
			localC[i][j] = 0;
		}
	}

	int** multiplyRes = NULL;
	if (allocMatrix(&multiplyRes, blockDim, blockDim) != 0) {
		printf("[ERROR] Matrix alloc for multiplyRes in rank %d failed!\n", rank);
		MPI_Abort(MPI_COMM_WORLD, 8);
	}
	for (int k = 0; k < procDim; k++) {
		matrixMultiply(localA, localB, blockDim, blockDim, &multiplyRes);

		for (int i = 0; i < blockDim; i++) {
			for (int j = 0; j < blockDim; j++) {
				localC[i][j] += multiplyRes[i][j];
			}
		}
		// Shift A once (left) and B once (up)
		MPI_Cart_shift(cartComm, 1, 1, &left, &right);
		MPI_Cart_shift(cartComm, 0, 1, &up, &down);
		MPI_Sendrecv_replace(&(localA[0][0]), blockDim * blockDim, MPI_INT, left, 1, right, 1, cartComm, MPI_STATUS_IGNORE);
		MPI_Sendrecv_replace(&(localB[0][0]), blockDim * blockDim, MPI_INT, up, 1, down, 1, cartComm, MPI_STATUS_IGNORE);
	}
	
	// Gather results
	MPI_Gatherv(&(localC[0][0]), rows * columns / worldSize, MPI_INT,
		globalptrC, sendCounts, displacements, subarrtype,
		0, MPI_COMM_WORLD);

	
	freeMatrix(&localC);
	freeMatrix(&multiplyRes);

	if (rank == 0) {
		std::chrono::steady_clock::time_point end = std::chrono::steady_clock::now();
		std::cout << "\nElapsed " << std::chrono::duration_cast<std::chrono::milliseconds>(end - begin).count() << " ms" << std::endl;
		//******************************************************************************************************************************printf("C is:\n");
		//******************************************************************************************************************************printMatrix(C, rows);		
	}
	
	// Finalize the MPI environment
	MPI_Finalize();

	return 0;
}