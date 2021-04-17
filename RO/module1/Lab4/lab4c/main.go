package main

import (
	"fmt"
	"math/rand"
	"sync"
)

type ConcurrentGraph struct {
	matrix [][]int
	lock   sync.RWMutex
}

func (g *ConcurrentGraph) print() {
	g.lock.RLock()
	for _, line := range g.matrix {
		for _, i := range line {
			fmt.Printf("%d ", i)
		}
		fmt.Println()
	}
	g.lock.RUnlock()
}

func (g *ConcurrentGraph) size() int {
	g.lock.RLock()
	s := len(g.matrix)
	g.lock.RUnlock()
	return s
}

func (g *ConcurrentGraph) addVertex() {
	g.lock.Lock()
	for i := 0; i < len(g.matrix); i++ {
		g.matrix[i] = append(g.matrix[i], 0)
	}
	g.matrix = append(g.matrix, make([]int, len(g.matrix)+1))
	g.lock.Unlock()
}

func (g *ConcurrentGraph) addEdge(from, to int) {
	g.lock.Lock()
	if from < len(g.matrix) || to < len(g.matrix) {
		g.matrix[from][to] = 1
		g.matrix[to][from] = 1
	}
	g.lock.Unlock()
}

func (g *ConcurrentGraph) removeEdge(from, to int) {
	g.changeWeight(from, to, 0)
}

func (g *ConcurrentGraph) changeWeight(from, to, newWeight int) {
	g.lock.Lock()
	if from < len(g.matrix) && to < len(g.matrix) && g.matrix[from][to] != 0 {
		g.matrix[from][to] = newWeight
		g.matrix[to][from] = newWeight
	}
	g.lock.Unlock()
}

func (g *ConcurrentGraph) removeVertex(v int) {
	g.lock.Lock()
	for i := 0; i < len(g.matrix); i++ {
		g.matrix[i] = append(g.matrix[i][:v], g.matrix[i][v+1:]...)
	}
	g.matrix = append(g.matrix[:v], g.matrix[v+1:]...)
	g.lock.Unlock()
}

func (g *ConcurrentGraph) dfs(from, to int, visited []bool, weight int) int {
	for v, w := range g.matrix[from] {
		if w != 0 && v == to {
			visited[v] = true
			return weight + w
		}
		if w != 0 && !visited[v] {
			visited[v] = true
			weight = g.dfs(v, to, visited, weight)
			if weight > 0 {
				weight += w
			}
			return weight
		}
	}
	return 0
}

func (g *ConcurrentGraph) pathExists(from, to int) int {
	g.lock.RLock()
	price := 0
	if from < len(g.matrix) && to < len(g.matrix) {
		visited := make([]bool, len(g.matrix))
		visited[from] = true
		price = g.dfs(from, to, visited, price)
	}
	g.lock.RUnlock()
	return price
}

func editorPaths(g *ConcurrentGraph) {
	for i := 0; i < 30; i++ {
		from := rand.Intn(g.size())
		to := rand.Intn(g.size())
		for to == from {
			to = rand.Intn(g.size())
		}

		add := rand.Intn(2)
		if add == 1 {
			g.addEdge(from, to)
			fmt.Printf("Added path from %d to %d\n", from, to)
		} else {
			g.removeEdge(from, to)
			fmt.Printf("Removed path from %d to %d\n", from, to)
		}
	}
}

func editorTowns(g *ConcurrentGraph) {
	for i := 0; i < 30; i++ {
		add := rand.Intn(2)
		if add == 1 {
			g.addVertex()
			fmt.Println("Added new town!")
		} else {
			v := rand.Intn(g.size())
			g.removeVertex(v)
			fmt.Printf("Removed town %d\n", v)
		}
	}
}

func editorPrices(g *ConcurrentGraph) {
	for i := 0; i < 30; i++ {
		from := rand.Intn(g.size())
		to := rand.Intn(g.size())
		for to == from {
			to = rand.Intn(g.size())
		}
		w := rand.Intn(101)
		g.changeWeight(from, to, w)
		fmt.Printf("Changed price from %d to %d. New price = %d\n", from, to, w)
	}
}

func pathSearcher(g *ConcurrentGraph) {
	for i := 0; i < 30; i++ {
		from := rand.Intn(g.size())
		to := rand.Intn(g.size())
		for to == from {
			to = rand.Intn(g.size())
		}

		price := g.pathExists(from, to)
		if price > 0 {
			fmt.Printf("Is there a path from %d to %d? The answer is yes and it costs %d\n", from, to, price)
		} else {
			fmt.Printf("Is there a path from %d to %d? The answer is no\n", from, to)
		}

	}
}

const (
	V = 10
	E = 20
)

func main() {
	g := new(ConcurrentGraph)
	for i := 0; i < V; i++ {
		g.addVertex()
	}
	for i := 0; i < E; i++ {
		from := rand.Intn(g.size())
		to := rand.Intn(g.size())
		for to == from {
			to = rand.Intn(g.size())
		}
		g.addEdge(from, to)
	}

	var wg sync.WaitGroup
	wg.Add(5)
	go func() {
		editorPaths(g)
		wg.Done()
	}()
	go func() {
		editorTowns(g)
		wg.Done()
	}()
	go func() {
		editorPrices(g)
		wg.Done()
	}()
	go func() {
		pathSearcher(g)
		wg.Done()
	}()
	go func() {
		pathSearcher(g)
		wg.Done()
	}()

	wg.Wait()
}
