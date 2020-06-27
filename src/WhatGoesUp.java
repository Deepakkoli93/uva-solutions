import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhatGoesUp {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> list = new ArrayList<>();
        while(true) {
            String line = reader.readLine();
            if(line == null) break;
            list.add(Integer.parseInt(line));
        }
        int n = list.size();
        int[] m = new int[n+1];
        Arrays.fill(m, Integer.MAX_VALUE);
        m[0] = Integer.MIN_VALUE;
        int[] L = new int[n];
        for(int i=0;i<n;i++) {
            int ind = Arrays.binarySearch(m, list.get(i));
            if(ind<0) {
                ind = ind+1;
                ind = 0-ind;
            }
            while(ind > 0) {
                if (m[ind] == m[ind-1]) {
                    ind--;
                } else {
                    break;
                }
            }
            m[ind] = list.get(i);
            L[i] = ind;
        }
        int ind = Arrays.binarySearch(m, Integer.MAX_VALUE);
        if(ind<0) {
            ind = ind+1;
            ind = 0-ind;
        }
        while(ind > 0) {
            if (m[ind] == m[ind-1]) {
                ind--;
            } else {
                break;
            }
        }

        List<Integer> ans = new ArrayList<>();
        int maxl = L[0];
        int maxlind = 0;
        for(int i=0;i<n;i++) {
            if (L[i] >= maxl) {
                maxl = L[i];
                maxlind = i;
            }
        }
        System.out.println(maxl);
        System.out.println("-");
        ans.add(list.get(maxlind));
        int curr = maxlind;
        int currVal = list.get(curr);
        int lValToSearch = maxl-1;
        curr--;
        while(curr >= 0) {
            while(L[curr] != lValToSearch || list.get(curr) >= currVal) {
                curr--;
            }
            ans.add(list.get(curr));
            lValToSearch = L[curr]-1;
            currVal = list.get(curr);
            if (lValToSearch == 0) break;
        }
        int ss = ans.size();
        for(int i=ss-1; i>=0; i--) {
            System.out.println(ans.get(i));
        }
    }
}
