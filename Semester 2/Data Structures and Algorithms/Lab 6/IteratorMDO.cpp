#include "IteratorMDO.h"
#include "MDO.h"


IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	/* de adaugat */
	curentTD = 0;
	curentVECT = 0;
	for (int i = 0; i < dict.m; i++)
	{
		if (dict.elemente[i].first != -1)
		{
			ord.push_back(dict.elemente[i]);
		}
	}
	sort(ord.begin(), ord.end(), [this](TSectiune t1, TSectiune t2) 
		{ 
			return dict.rel(t1.first, t2.first); 
		});
}

void IteratorMDO::prim() {
	/* de adaugat */
	curentTD = 0;
	curentVECT = 0;
}

void IteratorMDO::urmator() {
	/* de adaugat */
	if (!valid())
		throw exception();
	if (curentVECT < ord.at(curentTD).second.size() - 1)
	{
		curentVECT++;
		return;
	}
	if (curentVECT == ord.at(curentTD).second.size() - 1)
	{
		curentVECT = 0;
		curentTD = curentTD + 1;
		return;
	}
}

bool IteratorMDO::valid() const {
	/* de adaugat */
	return curentTD < ord.size() && curentVECT < ord.at(curentTD).second.size();
}

TElem IteratorMDO::element() const {
	/* de adaugat */
	if (!valid())
		throw exception();
	return TElem(ord.at(curentTD).first, ord.at(curentTD).second.at(curentVECT));
}