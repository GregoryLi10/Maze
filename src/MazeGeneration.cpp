// Copyright Epic Games, Inc. All Rights Reserved.

#include "MazeGeneration.h"
#include "Modules/ModuleManager.h"

IMPLEMENT_PRIMARY_GAME_MODULE( FDefaultGameModuleImpl, MazeGeneration, "MazeGeneration" );

MazeGeneration::MazeGeneration(int width, int height) { //constructor initialize all variables and calls generate
	srand(time(0));
	this->width = width;
	this->height = height;
	maze = new unordered_set<Direction>*[height];
	for (int i = 0; i < height; i++) {
		maze[i] = new unordered_set<Direction>[width];
	}
	start = { (int)(rand() % height), (int)(rand() % width) };
	MazeGeneration::visited = new unordered_set<vector<int>, VectorHash>(); //ERROR
	MazeGeneration::pEndpoints = new unordered_set<vector<int>, VectorHash>();
	backtrace = new stack<vector<int>>();
	backtraceDir = new stack<Direction>();
	generate(); //generate maze
}

void MazeGeneration::generate() {
	generate(start, false);
	
	//cout << pEndpoints; //prints potential endpoints for the maze
}


void MazeGeneration::generate(vector<int> curr, bool isDeadend) {
	if (curr == start && !visited->empty()) {
		return; //if it backtraces back to start, end generation
	}
	vector<Direction> possibles = *MazeGeneration::possibles(curr); //all possible directions from curr
	if (possibles.size() == 0) { //if deadend or backtracing
		if (!isDeadend) 
			MazeGeneration::pEndpoints->insert(curr); //add to potential endpoints if true deadend
		MazeGeneration::visited->insert(curr); //add point to visited
		Direction temp1 = backtraceDir->top();
		backtraceDir->pop();

		unordered_set<Direction>* dim1 = maze[curr.at(0)];
		dim1[curr.at(1)].insert(temp1);
		
		//add previous direction
//			System.out.println("["+curr.at(0)+", "+curr[1]+"]"); //print backtrace steps
		vector<int> temp2 = backtrace->top();
		backtrace->pop();
		MazeGeneration::generate(temp2, true); //go backwards by popping from stack
		return;
	}
	Direction dir = possibles.at(rand()%possibles.size()); //gets random valid direction
	visited->insert(curr); //adds to visited set
	backtrace->push(curr); //pushes to backtrace stack
	unordered_set<Direction>* dim1 = maze[curr.at(0)];
	dim1[curr.at(1)].insert(dir); //adds direction to cell
	vector<int> t2;
//		System.out.println("["+curr.at(0)+", "+curr[1]+"] "+dir); //prints step 
	switch (dir) { //pushes opposite direction for backtrace and moves to next cell
	case UP:
		backtraceDir->push(DOWN);
		MazeGeneration::generate({curr.at(0)-1, curr.at(1)}, false);
		break;
	case DOWN:
		backtraceDir->push(UP);
		MazeGeneration::generate( {curr.at(0) + 1, curr.at(1)}, false);
		break;
	case LEFT:
		backtraceDir->push(RIGHT);
		MazeGeneration::generate({curr.at(0), curr.at(1) - 1}, false);
		break;
	case RIGHT:
		backtraceDir->push(LEFT);
		MazeGeneration::generate({curr.at(0), curr.at(1) + 1}, false);
		break;
	}
}

vector<MazeGeneration::Direction>* MazeGeneration::possibles(vector<int> n) { //creates list of possible directions 
	vector<MazeGeneration::Direction>* possibles = new vector<MazeGeneration::Direction>();
	if (!(n.at(0) == 0 && visited->find({ n.at(0) - 1,n.at(1) }) != visited->end()))
		possibles->push_back(UP);
	if (!(n.at(0) == MazeGeneration::height - 1 && visited->find({ n.at(0) + 1, n.at(1) }) != visited->end()))
		possibles->push_back(DOWN);
	if (!(n.at(1) == 0 && visited->find({ n.at(0), n.at(1) - 1 }) != visited->end()))
		possibles->push_back(LEFT);
	if (!(n.at(1) == width - 1 && visited->find({ n.at(0), n.at(1) + 1 }) != visited->end()))
		possibles->push_back(RIGHT);
	return possibles;
}

map<bool, vector<int>> MazeGeneration::DirectionToCoords() {
	map<bool, vector<int>> map;
	return map;
}

//MazeGeneration::MazeGeneration(int n) { //constructor for square mazes
//	this(n, n);
//}

MazeGeneration::~MazeGeneration() {
	for (int i = 0; i < height; i++) {
		delete[] maze[i];
	}
	delete[] maze;
}