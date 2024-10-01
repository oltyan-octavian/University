import copy


def left_side(lst):
    for i in range(0, len(lst) - 1):
        if lst[i] < lst[i + 1]:
            return False
    return True


def right_side(lst):
    for i in range(0, len(lst) - 1):
        if lst[i] > lst[i + 1]:
            return False
    return True


def min_index(lst):
    mn = 100
    ind = 0
    for i in range(0, len(lst)):
        if lst[i] < mn:
            mn = copy.deepcopy(lst[i])
            ind = copy.deepcopy(i)
    return ind


def consistent(lst):
    for i in range(0, len(lst)-1):
        for j in range(i+1, len(lst)):
            if lst[i] == lst[j]:
                return False
    index = min_index(lst)
    if left_side(lst[:index]) is True and right_side(lst[index:]) is True:
        return True
    return False


def solution(lst, temp):
    if len(lst) == len(temp):
        return True
    return False


def solution_found(lst):
    print(lst)


def solution_found_iterativ(lst, temp):
    afisare = []
    for i in temp:
        afisare.append(lst[i])
    print(afisare)


def recursiv(lst, temp):
    index = 0
    temp.append(lst[index])
    while index is not None and len(temp) <= len(lst):
        temp[-1] = lst[index]
        if consistent(temp):
            if solution(lst, temp):
                solution_found(temp)
            recursiv(lst, temp[:])
        if index + 1 < len(lst):
            index = index + 1
        else:
            index = None


def iterativ(lst):
    temp = [-1]
    while len(temp) > 0:
        chosen = False
        while not chosen and temp[-1] < len(lst) - 1:
            temp[-1] = temp[-1] + 1
            chosen = consistent(temp)
        if chosen:
            if solution(lst, temp):
                solution_found_iterativ(lst, temp)
            temp.append(-1)
        else:
            temp.pop()


def run():
    n = int(input("Cate numere doriti sa cititi?: "))
    nums = []
    for i in range(0, n):
        x = int(input("Introduceti numarul citit: "))
        nums.append(x)
    while True:
        option = input("Alegeti optiunea dorita (recursiv, iterativ, exit): ")
        if option == 'recursiv':
            recursiv(nums, [])
        elif option == 'iterativ':
            iterativ(nums)
        elif option == 'exit':
            return False
        else:
            print("Optiunea nu exista!")


run()
