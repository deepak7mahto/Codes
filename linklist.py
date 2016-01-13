class NodeStructure:
    def __init__(self, value, tail=None):
        self.value = value
        self.next = tail


class Q:
    def __init__(self, *start):
        self.head = None
        self.stop = None
        for i in start:
            self.add(i)

    def pop(self):
        if self.head is None:
            raise Exception("No element in the queue")
        else:
            value = self.head.value
            self.head = self.head.next
            if self.head is None:  # when the only element is removed from the queue the queue is empty
                self.tail = None
            return value

    def add(self, val):
        Newnode = NodeStructure(val)
        if self.head is None:
            self.head = self.stop = Newnode  # it is the first node in the queue
        else:
            self.stop.next = Newnode
            self.stop = Newnode

    def __iter__(self):
        u = self.head
        while u is not self.stop:
            yield u.value
            u = u.next

    def __repr__(self):
        if self.head is None:
            return "Queue:[...EMPTY...]"

        return "Queue:[{0:s}]".format(','.join(map(str, self)))

    def checklist(self):
        p1 = p2 = self
        while p1 != None and p2 != None:
            if p2.Next == None:  # when only single node is there
                return False
            p1 = p1.next
            p2 = p1.next.next
            if p1 == p2:
                return True  # cycle found hence return True
            return False


def main():
    s = Q()
    s.add(1)
    s.add(10)
    s.add(100)
    s.add(1000)
    s.add(10000)
    s.add(100000)
    s.add(1000000)
    s.add(10000000)
    s.add(100000000)
    s.pop()
    s.pop()
    print(s)


if __name__ == '__main__':
    main()
