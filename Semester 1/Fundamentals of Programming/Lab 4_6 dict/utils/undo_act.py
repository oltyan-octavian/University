from utils import cpy


def add_undo(undo_list: list, crt_list: list):
    new_list = cpy.list_copy(crt_list)
    undo_list.append(new_list)


def remove_undo(undo_list: list):
    undo_list.pop()


