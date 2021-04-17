package main

import (
	"fmt"
	"sync"
)

const (
	NUM    = 10
	Q1_CAP = 1
	Q2_CAP = 2
)

var output chan string = make(chan string, 100)

func Ivanov(q1 chan<- bool) {
	for i := 0; i < NUM; i++ {
		fmt.Println("Ivanov sending item...")
		q1 <- true
	}
	close(q1)
}

func Petrov(q1 <-chan bool, q2 chan<- bool) {
	for {
		item, open := <-q1
		if !open {
			break
		} else {
			fmt.Println("Pertov recieved item")
			fmt.Println("Pertov sending item...")
			q2 <- item

		}
	}
	close(q2)
}

func Nechyporchuk(q2 <-chan bool) int {
	sum := 0
	for {
		_, open := <-q2
		if !open {
			return sum
		} else {
			fmt.Println("Nechyporuk recieved item")
			sum += 1
		}
	}
}

func main() {
	q1, q2 := make(chan bool, Q1_CAP), make(chan bool, Q2_CAP)
	var wg sync.WaitGroup
	sum := 0
	wg.Add(3)

	go func() {
		Ivanov(q1)
		wg.Done()
	}()
	go func() {
		Petrov(q1, q2)
		wg.Done()
	}()
	go func() {
		sum = Nechyporchuk(q2)
		wg.Done()
	}()

	wg.Wait()

	fmt.Println(sum)
}
