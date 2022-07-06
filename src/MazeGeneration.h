// Copyright Epic Games, Inc. All Rights Reserved.

#pragma once

#include "CoreMinimal.h"
#include<iostream>
#include<String>
#include<vector>
#include<stack>
#include<unordered_set>
#include<map>
#include<time.h>
using namespace std;

UCLASS()
class MazeGeneration  {
public:
	MazeGeneration(int width, int height);
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
	map<bool, vector<int>> DirectionToCoords();

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly)
		int width, height;

	UPROPERTY(VisibleAnywhere, BlueprintReadOnly)
		unordered_set<vector<int>, VectorHash>* pEndpoints; //potential endpoints set
	~MazeGeneration();

	

private:
	void generate(vector<int> curr, bool isDeadend);
	vector<Direction>* possibles(vector<int> n);
	unordered_set<vector<int>, VectorHash>* visited; //visited set
	unordered_set<Direction>** maze;	
	vector<int> start; //start cell
	stack<vector<int>>* backtrace; //backtrace stack
	stack<Direction>* backtraceDir; //backtrace direction stack
};