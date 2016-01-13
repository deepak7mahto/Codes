""" Simple circular buffer implementation """
class C:
    def __init__(self,size):
        self.count = 0
        self.size = size
        self.buffer = [None]*size
        self.low = 0
        self.high = 0


    def isEmpty(self):
        return self.count == 0

    def isFull(self):
        return self.count == self.size

    def add(self, value):
        if self.isFull():
            self.low = (self.low + 1) % self.size
        else:
            self.count += 1

        self.buffer[self.high] = value
        self.high = (self.high + 1) % self.size

    def remove(self):
        if self.count == 0:
            raise Exception("circular Queue is empty")
        value = self.buffer[self.low]
        self.low = (self.low + 1) % self.size
        self.count -= 1
        return value

    def __iter__(self):
        index = self.low
        element_in_queue = self.count
        while element_in_queue > 0 :
            yield self.buffer[index]
            index = (index+1) % self.size
            element_in_queue -= 1

    def __repr__(self):
        if self.isEmpty():
            return "cb[]"
        return 'cb:[' + ',' .join(map(str, self)) + ']'

def main():
    c = C(20)
    print(c.count)
   

if __name__ == '__main__':
    main()
