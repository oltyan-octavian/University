#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include "console.h"
#include "repo.h"
#include "service.h"
#include "tests.h"
#include "validator.h"

int main()
{
	Repository repo;
	Validator valid;
	Service serv{ repo, valid };
	Console ui{ serv };
	testAll();
	ui.start();
	_CrtDumpMemoryLeaks();
}