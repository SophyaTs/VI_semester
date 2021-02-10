package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const (
	CUSTOMERS  = 10
	GOROUTINES = 3
)

func generateName() string {
	lastnames := []string{"Black", "Mayers", "Washington", "Turner", "Bones", "Greenwood", "Darcy", "Snape"}
	names := []string{"Roger", "Amanda", "George", "Fitzwilliam", "Severus", "Anne", "Charlotte", "Mathilda"}
	return names[rand.Intn(len(names))] + " " + lastnames[rand.Intn(len(lastnames))]
}

func hairdresser(queue <-chan string) {
	served := 0
	for {
		customer := <-queue
		fmt.Printf("%s getting a haircut...\n", customer)
		time.Sleep(time.Duration(rand.Intn(3)) * time.Second)
		served++
		if served == CUSTOMERS {
			return
		}
	}
}

func customer(nameinput <-chan string, queue chan<- string) {
	for {
		name, open := <-nameinput
		if open {
			time.Sleep(time.Duration(rand.Intn(3)) * time.Second)
			fmt.Printf("%s gets in line\n", name)
			queue <- name
		} else {
			return
		}
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())
	customers, queue := make(chan string, CUSTOMERS), make(chan string, CUSTOMERS)
	var wg sync.WaitGroup
	wg.Add(GOROUTINES + 1)

	go func() {
		hairdresser(queue)
		wg.Done()
	}()
	for i := 0; i < GOROUTINES; i++ {
		go func() {
			customer(customers, queue)
			wg.Done()
		}()
	}

	for i := 0; i < CUSTOMERS; i++ {
		customers <- generateName()
	}
	close(customers)

	wg.Wait()
}
