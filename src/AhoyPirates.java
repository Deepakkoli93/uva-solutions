import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AhoyPirates {

    static class SegmentTree {

//        private Node[] heap;
        private int[] array;
        private int size;
        private int arrsize;
        private int[] heapSums;
        private UpdateOp[] heapPendingValues;
        private int[] heapFroms;
        private int[] heapTos;

        public SegmentTree(int[] array, int arrsize) {
            this.array = array;
            this.arrsize = arrsize;
            //The max size of this array is about 2 * 2 ^ log2(n) + 1
            size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) arrsize) / Math.log(2.0)) + 1)));
//            heap = new Node[size];
            heapSums = new int[size];
            heapPendingValues = new UpdateOp[size];
            heapFroms = new int[size];
            heapTos = new int[size];
            build(1, 0, arrsize);
        }

        //Initialize the Nodes of the Segment tree
        private void build(int v, int from, int size) {
//            heap[v] = new Node();
            heapFroms[v] = from;
            heapTos[v] = from + size - 1;

            if (size == 1) {
                heapSums[v] = array[from];
            } else {
                build(2 * v, from, size / 2);
                build(2 * v + 1, from + size / 2, size - size / 2);

                heapSums[v] = heapSums[2 * v] + heapSums[2 * v + 1];
            }
        }

        public int rsq(int from, int to) {
            return rsq(1, from, to);
        }

        private int rsq(int v, int from, int to) {
//            Node n = heap[v];

            //If you did a range update that contained this node, you can infer the Sum without going down the tree
            if (heapPendingValues[v] != null && contains(heapFroms[v], heapTos[v], from, to)) {
                if (heapPendingValues[v] == UpdateOp.SET) {
                    return (to - from + 1);
                } else if(heapPendingValues[v] == UpdateOp.UNSET) {
                    return 0;
                }

            }

            if (contains(from, to, heapFroms[v], heapTos[v])) {
                return heapSums[v];
            }

            if (intersects(from, to, heapFroms[v], heapTos[v])) {
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
//            Node n = heap[v];

            /**
             * If the updating-range contains the portion of the current Node  We lazily update it.
             * This means We do NOT update each position of the vector, but update only some temporal
             * values into the Node; such values into the Node will be propagated down to its children only when they need to.
             */
            if (contains(from, to, heapFroms[v], heapTos[v])) {
                change(v, updateOp);
            }

            if (heapFroms[v] - heapTos[v] + 1 == 1) return;

            if (intersects(from, to, heapFroms[v], heapTos[v])) {
                /**
                 * Before keeping going down to the tree We need to propagate the
                 * the values that have been temporally/lazily saved into this Node to its children
                 * So that when We visit them the values  are properly updated
                 */
                propagate(v);

                update(2 * v, from, to, updateOp);
                update(2 * v + 1, from, to, updateOp);

                heapSums[v] = heapSums[2 * v] + heapSums[2 * v + 1];
//                n.min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
            }
        }

        //Propagate temporal values to children
        private void propagate(int v) {
//            Node n = heap[v];

            if (heapPendingValues[v] != null) {
                change(2 * v, heapPendingValues[v]);
                change(2 * v + 1, heapPendingValues[v]);
                heapPendingValues[v] = null; //unset the pending propagation value
            }
        }

        //Save the temporal values that will be propagated lazily
        private void change(int v, UpdateOp updateOp) {

            if (updateOp == UpdateOp.SET) {
                heapSums[v] = heapFroms[v] - heapTos[v] + 1;
                array[heapFroms[v]] = 1;
                heapPendingValues[v] = updateOp;
            } else if (updateOp == UpdateOp.UNSET) {
                heapSums[v] = 0;
                array[heapFroms[v]] = 0;
                heapPendingValues[v] = updateOp;
            } else if (updateOp == UpdateOp.FLIP) {
                heapSums[v] = heapFroms[v] - heapTos[v] + 1 - heapSums[v];
                if (array[heapFroms[v]] == 1) {
                    array[heapFroms[v]] = 0;
                } else if (array[heapFroms[v]] == 0) {
                    array[heapFroms[v]] = 1;
                }
                if (heapPendingValues[v] == UpdateOp.SET) {
                    heapPendingValues[v] = UpdateOp.UNSET;
                } else if (heapPendingValues[v] == UpdateOp.UNSET) {
                    heapPendingValues[v] = UpdateOp.SET;
                } else if (heapPendingValues[v] == UpdateOp.FLIP) {
                    heapPendingValues[v] = UpdateOp.DO_NOTHING;
                }
            }
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
