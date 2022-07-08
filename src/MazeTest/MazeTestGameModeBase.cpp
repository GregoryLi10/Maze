// Copyright Epic Games, Inc. All Rights Reserved.


#include "MazeTestGameModeBase.h"
#include <set>

AMazeTestGameModeBase::AMazeTestGameModeBase() {
	this->width = 5;
	this->height = 5;
	this->depth = 5;
}

AMazeTestGameModeBase::AMazeTestGameModeBase(int width, int height, int depth) {
	this->width = width;
	this->height = height;
	this->depth = depth;
}

UFUNCTION(BlueprintImplementableEvent)
void AMazeTestGameModeBase::Generate() {
	MazeGeneration maze(width, height);
	map<bool, std::vector<int>> coords = maze.DirectionToCoords(10);

	for (int i = 0; i < coords[false].size(); i++) {
		this->horizontal.Add(coords[false][i]);
	}
	for (int i = 0; i < coords[true].size(); i++) {
		this->vertical.Add(coords[true][i]);
	}
	for (int i = 0; i < 2; i++) {
		start.Add(maze.start[i]);
	}

	for (auto it = maze.pEndpoints->begin(); it != maze.pEndpoints->end(); it++) {
		pEndpoints.Add(it->at(0));
		pEndpoints.Add(it->at(1));
	}
}
