from utils import modify
from utils import sort
from utils import filter


def generate_list():
    p1 ={
        "scor1": 10,
        "scor2": 10,
        "scor3": 5,
        "scor4": 3,
        "scor5": 6,
        "scor6": 2,
        "scor7": 7,
        "scor8": 8,
        "scor9": 9,
        "scor10": 10,
        "scortotal": 70
    }
    p2 = {
        "scor1": 7,
        "scor2": 10,
        "scor3": 3,
        "scor4": 6,
        "scor5": 6,
        "scor6": 2,
        "scor7": 5,
        "scor8": 8,
        "scor9": 8,
        "scor10": 7,
        "scortotal": 62
    }
    p3 = {
        "scor1": 10,
        "scor2": 2,
        "scor3": 5,
        "scor4": 6,
        "scor5": 4,
        "scor6": 6,
        "scor7": 7,
        "scor8": 8,
        "scor9": 9,
        "scor10": 10,
        "scortotal": 67
    }
    p4 = {
        "scor1": 1,
        "scor2": 2,
        "scor3": 3,
        "scor4": 4,
        "scor5": 5,
        "scor6": 6,
        "scor7": 7,
        "scor8": 8,
        "scor9": 9,
        "scor10": 10,
        "scortotal": 55
    }
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
    assert(l[3]["scortotal"] == 70)
    assert(l[1]["scortotal"] == 62)


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
