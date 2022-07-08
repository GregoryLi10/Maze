#pragma once

#include<iostream>
#include<string>
#include<vector>
#include<stack>
#include<unordered_set>
#include<map>
#include<time.h>
using namespace std;

class MazeGeneration  {
public:
	MazeGeneration(int width, int height);
	// MazeGeneration(int n);
	struct VectorHash {
		size_t operator()(const std::vector<int>& v) const {
			std::hash<int> hasher;
			size_t seed = 0;
			for (int i : v) {
				seed ^= hasher(i) + 0x9e3779b9 + (seed << 6) + (seed >> 2);
			}
			return seed;
		}
	};
	enum Direction {
		UP, DOWN, LEFT, RIGHT
	};
	void generate();
	void draw();
	map<bool, vector<int>> DirectionToCoords(int depth);
	int width, height;
	vector<int> start; //start cell
	unordered_set<Direction>** maze;

	unordered_set<vector<int>, VectorHash>* pEndpoints; //potential endpoints set
	~MazeGeneration();

	

private:
	void generate(vector<int> curr, bool isDeadend);
	vector<Direction>* possibles(vector<int> n);
	unordered_set<vector<int>, VectorHash>* visited; //visited set
	stack<vector<int> >* backtrace; //backtrace stack
	stack<Direction>* backtraceDir; //backtrace direction stack
};