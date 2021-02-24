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
	for _, line := range g.matrix {
		for _, i := range line {
			fmt.Printf("%d ", i)
		}
		fmt.Println()
	}
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
	g.matrix[from][to] = 1
	g.matrix[to][from] = 1
	g.lock.Unlock()
}

func (g *ConcurrentGraph) removeEdge(from, to int) {
	g.changeWeight(from, to, 0)
}

func (g *ConcurrentGraph) changeWeight(from, to, newWeight int) {
	g.lock.Lock()
	if g.matrix[from][to] != 0 {
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

func (g *ConcurrentGraph) dfs(from, to int, visited []bool) bool {
	for v, w := range g.matrix[from] {
		if w != 0 && v == to {
			visited[v] = true
			return true
		}
		if w != 0 && !visited[v] {
			visited[v] = true
			return g.dfs(v, to, visited)
		}
	}
	return false
}

func (g *ConcurrentGraph) pathExists(from, to int) bool {
	g.lock.RLock()
	visited := make([]bool, len(g.matrix))
	visited[from] = true
	answer := g.dfs(from, to, visited)
	g.lock.RUnlock()
	return answer
}

func adderEdges(g *ConcurrentGraph) {
	for i := 0; i < 15; i++ {
		from := rand.Intn(g.size())
		to := rand.Intn(g.size())
		for to == from {
			to = rand.Intn(g.size())
		}
		g.addEdge(from, to)
		fmt.Printf("Added path from %d to %d", from, to)
	}
}

func removerEdges(g *ConcurrentGraph) {
	for i := 0; i < 15; i++ {
		from := rand.Intn(g.size())
		to := rand.Intn(g.size())
		for to == from {
			to = rand.Intn(g.size())
		}
		g.removeEdge(from, to)
		fmt.Printf("Removed path from %d to %d", from, to)
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
	fmt.Println(g.pathExists(0, 1))
	fmt.Println(g.pathExists(0, 4))
	fmt.Println(g.pathExists(0, 2))
}
