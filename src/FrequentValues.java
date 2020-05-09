import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FrequentValues {

    static class SegmentTree {

        private Node[] heap;
        private int[] array;
        private int size;

        public SegmentTree(int[] array) {
            this.array = Arrays.copyOf(array, array.length);
            //The max size of this array is about 2 * 2 ^ log2(n) + 1
            size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
            heap = new Node[size];
            build(1, 0, array.length);
        }


        public int size() {
            return array.length;
        }

        //Initialize the Nodes of the Segment tree
        private void build(int v, int from, int size) {
            heap[v] = new Node();
            heap[v].from = from;
            heap[v].to = from + size - 1;

            if (size == 1) {
                heap[v].sum = array[from];
                heap[v].min = array[from];
                heap[v].freqData = new FreqData(1, array[from], array[from], 1, array[from], 1);
            } else {
                //Build childs
                build(2 * v, from, size / 2);
                build(2 * v + 1, from + size / 2, size - size / 2);

                heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
                //min = min of the children
                heap[v].min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);

                FreqData left = heap[2 * v].freqData;
                FreqData right = heap[2 * v + 1].freqData;
                if (left.freqVal == right.freqVal) {
                    int mostFreqNum = left.freqVal;
                    int highestFreq = left.frequency + right.frequency;
                    if (left.leftNumber == right.rightNumber) {
                        heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.rightFreq, right.rightNumber, left.leftFreq + right.rightFreq);
                    } else if (left.leftNumber == right.leftNumber) {
                        heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.leftFreq, right.rightNumber, right.rightFreq);
                    } else if (left.rightNumber == right.rightNumber) {
                        heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq + left.rightFreq);
                    } else {
                        heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq);
                    }
                } else {
                    if (left.rightNumber == right.leftNumber) {
                        int highestFreq = Math.max(left.rightFreq + right.leftFreq, Math.max(left.frequency, right.frequency));
                        int mostFreqNum = left.freqVal;
                        if (highestFreq == right.frequency) mostFreqNum = right.freqVal;
                        if (highestFreq == left.rightFreq + right.leftFreq) mostFreqNum = left.rightNumber;
                        if (left.leftNumber == right.rightNumber) {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.rightFreq, right.rightNumber, left.leftFreq + right.rightFreq);
                        } else if (left.leftNumber == right.leftNumber) {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.leftFreq, right.rightNumber, right.rightFreq);
                        } else if (left.rightNumber == right.rightNumber) {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq + left.rightFreq);
                        } else {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq);
                        }
                    } else {
                        int highestFreq = Math.max(left.frequency, right.frequency);
                        int mostFreqNum = left.freqVal;
                        if (highestFreq == right.frequency) mostFreqNum = right.freqVal;
                        if (left.leftNumber == right.rightNumber) {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.rightFreq, right.rightNumber, left.leftFreq + right.rightFreq);
                        } else if (left.leftNumber == right.leftNumber) {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.leftFreq, right.rightNumber, right.rightFreq);
                        } else if (left.rightNumber == right.rightNumber) {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq + left.rightFreq);
                        } else {
                            heap[v].freqData = new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq);
                        }
                    }
                }
            }
        }

        public FreqData rFreq(int from, int to) {
            return rFreq(1, from, to);
        }

        private FreqData rFreq(int v, int from, int to) {
            Node n = heap[v];

            //If you did a range update that contained this node, you can infer the Sum without going down the tree
//            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
//                return (to - from + 1) * n.pendingVal;
//            }

            if (contains(from, to, n.from, n.to)) {
                return heap[v].freqData;
            }

            if (intersects(from, to, n.from, n.to)) {
//                propagate(v);
                FreqData left = rFreq(2 * v, from, to);
                FreqData right = rFreq(2 * v + 1, from, to);
                if (left.freqVal == right.freqVal) {
                    int mostFreqNum = left.freqVal;
                    int highestFreq = left.frequency + right.frequency;
                    if (left.leftNumber == right.rightNumber) {
                        return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.rightFreq, right.rightNumber, left.leftFreq + right.rightFreq);
                    } else if (left.leftNumber == right.leftNumber) {
                        return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.leftFreq, right.rightNumber, right.rightFreq);
                    } else if (left.rightNumber == right.rightNumber) {
                        return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq + left.rightFreq);
                    } else {
                        return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq);
                    }
                } else {
                    if (left.rightNumber == right.leftNumber) {
                        int highestFreq = Math.max(left.rightFreq + right.leftFreq, Math.max(left.frequency, right.frequency));
                        int mostFreqNum = left.freqVal;
                        if (highestFreq == right.frequency) mostFreqNum = right.freqVal;
                        if (highestFreq == left.rightFreq + right.leftFreq) mostFreqNum = left.rightNumber;
                        if (left.leftNumber == right.rightNumber) {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.rightFreq, right.rightNumber, left.leftFreq + right.rightFreq);
                        } else if (left.leftNumber == right.leftNumber) {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.leftFreq, right.rightNumber, right.rightFreq);
                        } else if (left.rightNumber == right.rightNumber) {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq + left.rightFreq);
                        } else {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq);
                        }
                    } else {
                        int highestFreq = Math.max(left.frequency, right.frequency);
                        int mostFreqNum = left.freqVal;
                        if (highestFreq == right.frequency) mostFreqNum = right.freqVal;
                        if (left.leftNumber == right.rightNumber) {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.rightFreq, right.rightNumber, left.leftFreq + right.rightFreq);
                        } else if (left.leftNumber == right.leftNumber) {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq + right.leftFreq, right.rightNumber, right.rightFreq);
                        } else if (left.rightNumber == right.rightNumber) {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq + left.rightFreq);
                        } else {
                            return new FreqData(highestFreq, mostFreqNum, left.leftNumber, left.leftFreq, right.rightNumber, right.rightFreq);
                        }
                    }
                }
            }

            return new FreqData(0,0,0,0,0,0);
        }

        public int rsq(int from, int to) {
            return rsq(1, from, to);
        }

        private int rsq(int v, int from, int to) {
            Node n = heap[v];

            //If you did a range update that contained this node, you can infer the Sum without going down the tree
            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
                return (to - from + 1) * n.pendingVal;
            }

            if (contains(from, to, n.from, n.to)) {
                return heap[v].sum;
            }

            if (intersects(from, to, n.from, n.to)) {
                propagate(v);
                int leftSum = rsq(2 * v, from, to);
                int rightSum = rsq(2 * v + 1, from, to);

                return leftSum + rightSum;
            }

            return 0;
        }

        public int rMinQ(int from, int to) {
            return rMinQ(1, from, to);
        }

        private int rMinQ(int v, int from, int to) {
            Node n = heap[v];


            //If you did a range update that contained this node, you can infer the Min value without going down the tree
            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
                return n.pendingVal;
            }

            if (contains(from, to, n.from, n.to)) {
                return heap[v].min;
            }

            if (intersects(from, to, n.from, n.to)) {
                propagate(v);
                int leftMin = rMinQ(2 * v, from, to);
                int rightMin = rMinQ(2 * v + 1, from, to);

                return Math.min(leftMin, rightMin);
            }

            return Integer.MAX_VALUE;
        }

        public void update(int from, int to, int value) {
            update(1, from, to, value);
        }

        private void update(int v, int from, int to, int value) {

            //The Node of the heap tree represents a range of the array with bounds: [n.from, n.to]
            Node n = heap[v];

            /**
             * If the updating-range contains the portion of the current Node  We lazily update it.
             * This means We do NOT update each position of the vector, but update only some temporal
             * values into the Node; such values into the Node will be propagated down to its children only when they need to.
             */
            if (contains(from, to, n.from, n.to)) {
                change(n, value);
            }

            if (n.size() == 1) return;

            if (intersects(from, to, n.from, n.to)) {
                /**
                 * Before keeping going down to the tree We need to propagate the
                 * the values that have been temporally/lazily saved into this Node to its children
                 * So that when We visit them the values  are properly updated
                 */
                propagate(v);

                update(2 * v, from, to, value);
                update(2 * v + 1, from, to, value);

                n.sum = heap[2 * v].sum + heap[2 * v + 1].sum;
                n.min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
            }
        }

        //Propagate temporal values to children
        private void propagate(int v) {
            Node n = heap[v];

            if (n.pendingVal != null) {
                change(heap[2 * v], n.pendingVal);
                change(heap[2 * v + 1], n.pendingVal);
                n.pendingVal = null; //unset the pending propagation value
            }
        }

        //Save the temporal values that will be propagated lazily
        private void change(Node n, int value) {
            n.pendingVal = value;
            n.sum = n.size() * value;
            n.min = value;
            array[n.from] = value;

        }

        //Test if the range1 contains range2
        private boolean contains(int from1, int to1, int from2, int to2) {
            return from2 >= from1 && to2 <= to1;
        }

        //check inclusive intersection, test if range1[from1, to1] intersects range2[from2, to2]
        private boolean intersects(int from1, int to1, int from2, int to2) {
            return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                    || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
        }

        static class FreqData {
            int frequency;
            int freqVal;
            int leftNumber;
            int leftFreq;
            int rightNumber;
            int rightFreq;
            public FreqData(int x, int y, int a, int b, int c, int d) {
                frequency = x;
                freqVal = y;
                leftNumber = a;
                leftFreq = b;
                rightNumber = c;
                rightFreq = d;
            }
        }
        //The Node class represents a partition range of the array.
        static class Node {
            int sum;
            int min;
            FreqData freqData;
            //Here We store the value that will be propagated lazily
            Integer pendingVal = null;
            int from;
            int to;

            int size() {
                return to - from + 1;
            }

        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String line = reader.readLine();
            if (line.equalsIgnoreCase("0")) break;
            String[] arr = line.split("\\s+");
            int n = Integer.parseInt(arr[0]);
            int q = Integer.parseInt(arr[1]);
            String[] arr1 = reader.readLine().split("\\s+");
            int[] values = new int[n];
            for(int i=0; i<n; i++) {
                values[i] = Integer.parseInt(arr1[i]);
            }
            SegmentTree segmentTree = new SegmentTree(values);
            while(q-- > 0) {
                String[] arr2 = reader.readLine().split("\\s+");
                int i = Integer.parseInt(arr2[0]);
                int j = Integer.parseInt(arr2[1]);
                SegmentTree.FreqData ans = segmentTree.rFreq(i - 1, j - 1);
                System.out.println(ans.frequency);
            }
        }
    }
}
