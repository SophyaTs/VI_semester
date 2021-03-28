#include <chrono>
#include <iostream>
#include "mpi.h"

int N;

void initMatrix(int **ptr) {
    *ptr = new int[N * N];
}

void releaseMatrix(int *ptr) {
    delete[] ptr;
}

int at(int i, int j) {
    return i * N + j;
}

void MatrixMultiply(int *A, int *B, int *C) {
    int GLOBAL_SIZE = 0;
    int GLOBAL_ID = 0;
    int ITEM_ROWS;
    int ITEM_OFFSET;
    MPI_Status status;

    MPI_Comm_rank(MPI_COMM_WORLD, &GLOBAL_ID);
    MPI_Comm_size(MPI_COMM_WORLD, &GLOBAL_SIZE);

    if (GLOBAL_ID == 0) {
        ITEM_ROWS = N / GLOBAL_SIZE;
        ITEM_OFFSET = ITEM_ROWS;

        std::cout<<ITEM_ROWS<<std::endl;

        for (int receiver = 1; receiver < GLOBAL_SIZE; receiver++) {
            MPI_Send(&ITEM_OFFSET, 1, MPI_INT, receiver, 1, MPI_COMM_WORLD);
            MPI_Send(&ITEM_ROWS, 1, MPI_INT, receiver, 1, MPI_COMM_WORLD);
            MPI_Send(&A[ITEM_OFFSET*N], ITEM_ROWS*N, MPI_INT, receiver, 1, MPI_COMM_WORLD);
            MPI_Send(B, N*N, MPI_INT, receiver, 1, MPI_COMM_WORLD);

            ITEM_OFFSET += ITEM_ROWS;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < ITEM_ROWS; j++) {
                C[at(j, i)] = 0;
                for (int k = 0; k < N; k++) {
                    C[at(j, i)] += A[at(j, k)] * B[at(k, i)];
                }
            }
        }

        for (int i = 1; i < GLOBAL_SIZE; i++) {
            MPI_Recv(&ITEM_OFFSET, 1, MPI_INT, i, 2, MPI_COMM_WORLD, &status);
            MPI_Recv(&ITEM_ROWS, 1, MPI_INT, i, 2, MPI_COMM_WORLD, &status);
            MPI_Recv(&C[ITEM_OFFSET*N], ITEM_ROWS*N, MPI_INT, i, 2, MPI_COMM_WORLD, &status);
        }

    } else {
        MPI_Recv(&ITEM_OFFSET, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
        MPI_Recv(&ITEM_ROWS, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
        MPI_Recv(&A[ITEM_OFFSET*N], ITEM_ROWS*N, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
        MPI_Recv(B, N*N, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);

        for (int i = 0; i < N; i++) {
            for (int j = ITEM_OFFSET; j < ITEM_ROWS + ITEM_OFFSET; j++) {
                C[at(j, i)] = 0;
                for (int k = 0; k < N; k++) {
                    C[at(j, i)] += A[at(j, k)] * B[at(k, i)];
                }
            }
        }

        MPI_Send(&ITEM_OFFSET, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
        MPI_Send(&ITEM_ROWS, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
        MPI_Send(&C[ITEM_OFFSET*N], ITEM_ROWS*N, MPI_INT, 0, 2, MPI_COMM_WORLD);
    }
}

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);

    N = atoi(argv[1]);

    int GLOBAL_ID;
    MPI_Comm_rank(MPI_COMM_WORLD, &GLOBAL_ID);
    
    int *A;
    int *B;
    int *C;
    initMatrix(&A);
    initMatrix(&B);
    initMatrix(&C);


    auto start = std::chrono::high_resolution_clock::now();
    MatrixMultiply(A, B, C);  
    if(GLOBAL_ID == 0){
        auto stop = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(stop - start);
        std::cout << "Time: " << duration.count() << " milliseconds" << std::endl;
    } 
    
    releaseMatrix(A);
    releaseMatrix(B);
    releaseMatrix(C);

    MPI_Finalize();
     
}