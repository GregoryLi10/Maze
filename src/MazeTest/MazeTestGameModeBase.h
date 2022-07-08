// Copyright Epic Games, Inc. All Rights Reserved.

#pragma once

#include "CoreMinimal.h"
#include "GameFramework/GameModeBase.h"
#include "MazeGeneration.h"
#include "Containers/Array.h"
#include "MazeTestGameModeBase.generated.h"

/**
 * 
 */
UCLASS()
class MAZETEST_API AMazeTestGameModeBase : public AGameModeBase
{
	GENERATED_BODY()

public:
	AMazeTestGameModeBase();
	AMazeTestGameModeBase(int width, int height, int depth);

	UFUNCTION(BlueprintCallable)
	void Generate();

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		int width;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		int height;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		int depth;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		TArray<int> horizontal;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		TArray<int> vertical;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		TArray<int> start;

	UPROPERTY(EditAnywhere, BlueprintReadWrite)
		TArray<int> pEndpoints;
};
