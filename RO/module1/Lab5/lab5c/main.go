package main

import (
	"fmt"
	"math/rand"
	"strconv"
	"sync"
	"time"
)

type CyclicBarrier struct {
	generation int
	count      int
	parties    int
	trigger    *sync.Cond
}

func (b *CyclicBarrier) nextGeneration() {
	// signal completion of last generation
	b.trigger.Broadcast()
	b.count = b.parties
	// set up next generation
	b.generation++
}

func (b *CyclicBarrier) Await() {
	b.trigger.L.Lock()
	defer b.trigger.L.Unlock()

	generation := b.generation

	b.count--
	index := b.count
	//println(index)

	if index == 0 {
		b.nextGeneration()
	} else {
		for generation == b.generation {
			//wait for current generation complete
			b.trigger.Wait()
		}
	}
}

func NewCyclicBarrier(num int) *CyclicBarrier {
	b := CyclicBarrier{}
	b.count = num
	b.parties = num
	b.trigger = sync.NewCond(&sync.Mutex{})

	return &b
}

var arraySize = 5
var arr1 = []int{1, 2, 3, 4, 5}
var arr2 = []int{2, 2, 3, 4, 5}
var arr3 = []int{1, 5, 3, 4, 5}

func arraySums() (int, int, int) {
	var sum1, sum2, sum3 int = 0, 0, 0
	for i := 0; i < arraySize; i++ {
		sum1 += arr1[i]
		sum2 += arr2[i]
		sum3 += arr3[i]
	}
	return sum1, sum2, sum3
}

func printArrays() {
	fmt.Printf("arr1: ")
	for i := 0; i < arraySize; i++ {
		fmt.Printf(strconv.Itoa(arr1[i]) + ", ")
	}
	fmt.Printf("\narr2: ")
	for i := 0; i < arraySize; i++ {
		fmt.Printf(strconv.Itoa(arr2[i]) + ", ")
	}
	fmt.Printf("\narr3: ")
	for i := 0; i < arraySize; i++ {
		fmt.Printf(strconv.Itoa(arr3[i]) + ", ")
	}
	fmt.Printf("\n")
}

func arrayChanger(arr []int, b *CyclicBarrier) {
	for {
		rand.Seed(time.Now().UnixNano())
		operation := rand.Intn(2)
		index := rand.Intn(arraySize)

		if operation == 0 {
			arr[index] += 1
		} else {
			arr[index] -= 1
		}

		b.Await()
		var sum1, sum2, sum3 int = arraySums()

		if sum1 == sum2 && sum2 == sum3 {
			fmt.Printf("Sum of all arrays are equal and = %d\n", sum1)
			break
		} else {
			fmt.Printf("Sums of arrays are %d,%d,%d\n", sum1, sum2, sum3)
		}
	}
	printArrays()
}

func main() {
	b := NewCyclicBarrier(3)

	go arrayChanger(arr1, b)
	go arrayChanger(arr2, b)
	go arrayChanger(arr3, b)

	fmt.Scanln()
}
