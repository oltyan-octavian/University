from utils import modify
from utils import sort
from utils import filter


def generate_list():
    p1 = [10, 10, 5, 3, 6, 2, 7, 8, 9, 10, 70]
    p2 = [7, 10, 3, 6, 6, 2, 5, 8, 8, 7, 62]
    p3 = [10, 2, 5, 6, 4, 6, 7, 8, 9, 10, 67]
    p4 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 55]
    p = [p1, p2, p3, p4]
    return p


def test_modify():
    l = generate_list()
    modify.delete_part(l, 2)
    assert(len(l) == 3)
    l = generate_list()
    modify.delete_interval(l, 1, 3)
    assert(len(l) == 1)
    l = generate_list()


def test_sort():
    l = generate_list()
    sort.sortare(l)
    assert(l[3][10] == 70)
    assert(l[1][10] == 62)


def test_filter_multiple():
    l = generate_list()
    l = filter.filter_multiple(l, 70)
    assert(len(l) == 1)
    l = generate_list()
    l = filter.filter_multiple(l, 67)
    l = filter.filter_multiple(l, 55)
    assert(len(l) == 0)


def test_filter_lessthan():
    l = generate_list()
    l = filter.filter_lessthan(l, 100)
    assert(len(l) == 0)
    l = generate_list()
    l = filter.filter_lessthan(l, 67)
    assert(len(l) == 2)


def test_all():
    test_modify()
    test_sort()
    test_filter_multiple()
    test_filter_lessthan()
