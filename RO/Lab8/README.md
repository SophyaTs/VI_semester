1. Install openmpi
    (for linux run: sudo apt update && sudo apt install openmpi-bin libopenmpi-dev mpich)
2. Go to folder where files are located. To run them use this commands:
    ```mpicxx file.cpp -o name```
    ```mpiexec -np 2 ./name 10``` 
    (file name and name can be different if you wish; runs with 2 processes and generates matrixes 10x10)
