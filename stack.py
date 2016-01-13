class NodeStructure:
    def __init__(self, value, tail=None):
        self.value = value
        self.next = tail


class Stack:
    def __init__(self, *start):
        self.head = None

        for node in start:
            self.prepend(node)

    def prepend(self, value):
        # add from the top
        self.head = NodeStructure(value, self.head)

    def pop(self):
        if self.head is None:
            raise Exception("Stack is empty ")

        val = self.head.value
        self.head = self.head.next
        return val

    def remove(self, value):
        # to remove any item from the stack
        n = self.head
        last = None
        while n is not None:
            if n.value == value:
                if last is None:
                    self.head = self.head.next
                else:
                    last.next = n.next
                return True
            last = n
            n = n.next
        return False

    def __iter__(self):
        n = self.head
        while n is not None:
            yield n.value
            n = n.next

    def __repr__(self):
        if self.head is None:
            return "Stack: [] "

        return "Stack:[ {0:s} ]".format(",".join((map(str, self))))


def main():
    s = Stack()
    s.prepend(10)
    s.prepend(20)
    s.prepend(30)
    s.prepend(40)
    print(s)
    s.pop()
    print(s)
    s.prepend(40)
    print(s)
    s.remove(40)
    print(s)
    s.remove(20)
    print(s)
if __name__ == '__main__':
    main()
