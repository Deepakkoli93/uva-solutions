import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static class SegmentTree {

        private Node[] heap;
        private int[] array;
        private int size;
        private int arrsize;
        private int[] heapsums;
        private UpdateOp[] heapPendingValues;
        private int[] heapFroms;
        private int[] heapTos;

        /**
         * Time-Complexity:  O(n*log(n))
         *
         * @param array the Initialization array
         */
        public SegmentTree(int[] array, int arrsize) {
            this.array = array;//Arrays.copyOf(array, arrsize);
            this.arrsize = arrsize;
            //The max size of this array is about 2 * 2 ^ log2(n) + 1
            size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) arrsize) / Math.log(2.0)) + 1)));
//            heap = new Node[size];
            heapsums = new int[size];
            heapPendingValues = new UpdateOp[size];
            heapFroms = new int[size];
            heapTos = new int[size];
            build(1, 0, arrsize);
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
            } else {
                build(2 * v, from, size / 2);
                build(2 * v + 1, from + size / 2, size - size / 2);

                heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
            }
        }

        public int rsq(int from, int to) {
            return rsq(1, from, to);
        }

        private int rsq(int v, int from, int to) {
            Node n = heap[v];

            //If you did a range update that contained this node, you can infer the Sum without going down the tree
            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
                if (n.pendingVal == UpdateOp.SET) {
                    return (to - from + 1);
                } else if(n.pendingVal == UpdateOp.UNSET) {
                    return 0;
                }

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

        public void update(int from, int to, UpdateOp updateOp) {
            update(1, from, to, updateOp);
        }

        private void update(int v, int from, int to, UpdateOp updateOp) {

            //The Node of the heap tree represents a range of the array with bounds: [n.from, n.to]
            Node n = heap[v];

            /**
             * If the updating-range contains the portion of the current Node  We lazily update it.
             * This means We do NOT update each position of the vector, but update only some temporal
             * values into the Node; such values into the Node will be propagated down to its children only when they need to.
             */
            if (contains(from, to, n.from, n.to)) {
                change(n, updateOp);
            }

            if (n.size() == 1) return;

            if (intersects(from, to, n.from, n.to)) {
                /**
                 * Before keeping going down to the tree We need to propagate the
                 * the values that have been temporally/lazily saved into this Node to its children
                 * So that when We visit them the values  are properly updated
                 */
                propagate(v);

                update(2 * v, from, to, updateOp);
                update(2 * v + 1, from, to, updateOp);

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
        private void change(Node n, UpdateOp updateOp) {

            if (updateOp == UpdateOp.SET) {
                n.sum = n.size();
                array[n.from] = 1;
                n.pendingVal = updateOp;
            } else if (updateOp == UpdateOp.UNSET) {
                n.sum = 0;
                array[n.from] = 0;
                n.pendingVal = updateOp;
            } else if (updateOp == UpdateOp.FLIP) {
                n.sum = n.size() - n.sum;
                if (array[n.from] == 1) {
                    array[n.from] = 0;
                } else if (array[n.from] == 0) {
                    array[n.from] = 1;
                }
                if (n.pendingVal == UpdateOp.SET) {
                    n.pendingVal = UpdateOp.UNSET;
                } else if (n.pendingVal == UpdateOp.UNSET) {
                    n.pendingVal = UpdateOp.SET;
                } else if (n.pendingVal == UpdateOp.FLIP) {
                    n.pendingVal = UpdateOp.DO_NOTHING;
                }
            }
//            n.min = value;
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

        enum UpdateOp {
            DO_NOTHING,
            SET,
            UNSET,
            FLIP
        }
        //The Node class represents a partition range of the array.
        static class Node {
            int sum;
//            int min;
            //Here We store the value that will be propagated lazily
            UpdateOp pendingVal = null;
            int from;
            int to;

            int size() {
                return to - from + 1;
            }

        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        StringBuilder stringBuilder = new StringBuilder();
        for(int tcase=1; tcase<=t; tcase++) {
            int m = Integer.parseInt(reader.readLine());
            int[] arr = new int[1024000];
            int arrsize=0;
            while(m-- > 0) {
                int rep = Integer.parseInt(reader.readLine());
                String str = reader.readLine();
                while(rep-- > 0) {
                    for(int i=0; i<str.length(); i++) {
                        if (str.charAt(i) == '0') {
                            arr[arrsize++] = 0;
                        } else {
                            arr[arrsize++] = 1;
                        }
                    }
                }
            }
            SegmentTree segmentTree = new SegmentTree(arr, arrsize);

            stringBuilder.append("Case " + tcase + ":" + '\n');
//            System.out.println("Case " + tcase + ":");
            int qcount = 1;
            int q = Integer.parseInt(reader.readLine());
            while(q-- > 0) {
                String[] qarr = reader.readLine().split("\\s+");
                int i = Integer.parseInt(qarr[1]);
                int j = Integer.parseInt(qarr[2]);
                if (qarr[0].equalsIgnoreCase("F")) {
                    segmentTree.update(i, j, SegmentTree.UpdateOp.SET);
                } else if (qarr[0].equalsIgnoreCase("E")) {
                    segmentTree.update(i, j, SegmentTree.UpdateOp.UNSET);
                } else if (qarr[0].equalsIgnoreCase("I")) {
                    segmentTree.update(i, j, SegmentTree.UpdateOp.FLIP);
                } else if (qarr[0].equalsIgnoreCase("S")) {
                    int ans = segmentTree.rsq(i, j);
//                    System.out.println("Q"+qcount+": " + ans);
                    stringBuilder.append("Q"+qcount+": " + ans + '\n');
                    qcount++;
                }
            }
        }
        System.out.print(stringBuilder);
    }
}
