#include <stdlib.h>
#include <stdio.h>
#include "mpi.h"
#include <time.h>
#include <sys/time.h>
#include <chrono>

void print_matrix(double *m, int n) {
  int row, col; 
  for (row=0; row<n;row++){
    for (col=0; col<n;col++){
      printf("%.0f\t", m[row*n+col]);
    }
    printf("\n");
  }
  printf("\n");
}

int main(int argc, char* argv[]) 
{
    const int N = atoi(argv[1]);
    int processCount, processId;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &processId);
    MPI_Comm_size(MPI_COMM_WORLD, &processCount);

    if(processId == 0){
        auto A = new double*[N];
        auto B = new double*[N];
        auto C = new double*[N];
        for(int i=0; i<N; ++i){
            A[i] = new double[N];
            B[i] = new double[N];
            C[i] = new double[N];
        }
        
        srand (time(NULL));
        for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++) {
                A[i][j]= rand()%10;
                B[i][j]= rand()%10;
            }
        }
        
        /* printf("\nA:\n");
        print_matrix(*A,N);
        printf("B:\n");
        print_matrix(*B,N);
 */
        std::chrono::steady_clock::time_point begin = std::chrono::steady_clock::now();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++){
                C[j][i] = 0;
                for (int k = 0; k < N; k++)
                    C[j][i] += A[j][k]*B[k][i];
            }
        std::chrono::steady_clock::time_point end = std::chrono::steady_clock::now();
        std::cout << "\nElapsed " << std::chrono::duration_cast<std::chrono::milliseconds>(end - begin).count() << " ms" << std::endl;
        /* printf("C:\n");
        print_matrix(*C,N);  */ 

        for(int i=0; i<N; ++i){
            delete A[i];
            delete B[i];
            delete C[i];
        }
        delete A; delete B; delete C;   
    }

    MPI_Finalize();
}