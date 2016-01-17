/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuproject;

import java.util.Random;

/**
 *
 * @author AMD
 */
public class sudokuClass {

    int grid[][] = {
        {0, 5, 0, 0, 0, 6, 0, 0, 7},
        {0, 0, 4, 0, 0, 0, 0, 0, 5},
        {0, 0, 0, 4, 9, 0, 6, 1, 0},
        {0, 0, 7, 0, 0, 4, 0, 0, 1},
        {0, 8, 2, 0, 0, 0, 7, 6, 0},
        {5, 0, 0, 8, 0, 0, 9, 0, 0},
        {0, 9, 6, 0, 3, 8, 0, 0, 0},
        {3, 0, 0, 0, 0, 0, 1, 0, 0},
        {7, 0, 0, 5, 0, 0, 0, 3, 0},};
    int status[][] = new int[9][9];
    int[] remaining = new int[9];
//int stack[9],top;
    int howManyLeft;

    sudokuClass() {
        int i, j;
        howManyLeft = 9;
        for (i = 0; i < 9; i++) {
            remaining[i] = i + 1;
            for (j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    status[i][j] = 2;
                } else {
                    status[i][j] = 0;
                }
            }
        }
    }

    void displayMat(int mat[][]) {
        int i, j;
        System.out.println("");
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                System.out.print(mat[i][j]);
                if ((j + 1) % 3 == 0) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
            if ((i + 1) % 3 == 0) {
                for (j = 0; j < 9; j++) {
                    System.out.print("__");
                }
                System.out.println("");
            }
        }
    }

    void displayAv(boolean av[][], int outerRank, int n) {
        int i, j, r, c;
        holder h = new holder();
        getCoordinates(outerRank, 1, h);
        r = h.r;
        c = h.c;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                System.out.print(" " + av[i][j]);
            }
            System.out.println("");
        }

    }

    boolean makeFitting() {
        boolean av[][] = new boolean[9][9], result = false, aux;
        int numberMap[] = {0, 0, 0, 0, 0, 0};
        int i, j;
        int number;
        if (howManyLeft == 0) {
            return true;
        }
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                av[i][j] = false;
            }
        }

        number = findFavorableNumber();
        howManyLeft--;

        for (i = 1; i <= 9; i++) {
            makeAvailabilityMap(av, i, number);
        }

        if (!nextNumberMap(av, numberMap, number, true)) {
            result = false;
        } else {
            aux = true;
            while ((aux) && (!makeFitting())) {
                aux = nextNumberMap(av, numberMap, number, false);
            }
            if (aux) {
                result = true;
            } else {
                result = false;
            }
        }
        if (!result) {
            remaining[number - 1] = number;
            reset(number);
            howManyLeft++;
        }
        return result;
    }

//    void getCoordinates(int outerRank, int innerRank, int r,int c)//IMPORTANT change
    void getCoordinates(int outerRank, int innerRank, holder h) {
        h.r = (outerRank - 1) / 3;
        h.r = 3 * h.r;
        switch (outerRank) {
            case 1:
            case 4:
            case 7:
                h.c = 0;
                break;
            case 2:
            case 5:
            case 8:
                h.c = 3;
                break;
            case 3:
            case 6:
            case 9:
                h.c = 6;
                break;
        }
        h.r += (innerRank - 1) / 3;
        switch (innerRank) {
            case 1:
            case 4:
            case 7:
                h.c += 0;
                break;
            case 2:
            case 5:
            case 8:
                h.c += 1;
                break;
            case 3:
            case 6:
            case 9:
                h.c += 2;
                break;
        }
    }

    void makeAvailabilityMap(boolean av[][], int outerRank, int n) {
        int i, j, r = 0, c = 0, ro = 0, co = 0;
        boolean rAvail[] = {true, true, true};
        boolean cAvail[] = {true, true, true};
        boolean boxPresent = false;
        holder h = new holder();
        getCoordinates(outerRank, 1, h);//changed
        r = h.r;
        c = h.c;
        boxPresent = false;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++) {
                if ((grid[r + i][c + j % 3] == n) && (status[r + i][c + j % 3] != 0)) {
                    boxPresent = true;
                    ro = r + i;
                    co = c + j % 3;
                    break;
                }
                if (grid[r + i][j] == n) {
                    rAvail[i] = false;
                }
                if (grid[j][c + i] == n) {
                    cAvail[i] = false;
                }
            }
            if (boxPresent) {
                break;
            }
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (status[r + i][c + j] != 0) {
                    av[r + i][c + j] = false;
                } else {
                    av[r + i][c + j] = ((!boxPresent) && rAvail[i] && cAvail[j]);
                }
            }
        }
        if (boxPresent) {
            av[ro][co] = true;
        }
    }

    void setPlace(boolean av[][], int outerRank, int innerRank, int n, boolean setBit) {
        int i, j, r = 0, c = 0;
        holder h = new holder();
        getCoordinates(outerRank, innerRank, h);//changed
        r = h.r;
        c = h.c;
        if (status[r][c] == 2) {
            return;//function fails
        }
        if (setBit) {
            getCoordinates(outerRank, 1, h);//changed
            r = h.r;
            c = h.c;
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    av[r + i][c + j] = false;
                }
            }
            getCoordinates(outerRank, innerRank, h);//changed
            r = h.r;
            c = h.c;
            for (i = 0; i < 9; i++) {
                av[r][i] = false;
                av[i][c] = false;
            }
            av[r][c] = true;
            grid[r][c] = n;
            status[r][c] = 1;
        } else {
            status[r][c] = 0;
            grid[r][c] = 0;
            makeAvailabilityMap(av, outerRank, n);
            switch (outerRank) {
                case 1:
                    makeAvailabilityMap(av, 2, n);
                    makeAvailabilityMap(av, 3, n);
                    makeAvailabilityMap(av, 4, n);
                    makeAvailabilityMap(av, 7, n);
                    break;
                case 2:
                    makeAvailabilityMap(av, 1, n);
                    makeAvailabilityMap(av, 3, n);
                    makeAvailabilityMap(av, 5, n);
                    makeAvailabilityMap(av, 8, n);
                    break;
                case 3:
                    makeAvailabilityMap(av, 1, n);
                    makeAvailabilityMap(av, 2, n);
                    makeAvailabilityMap(av, 6, n);
                    makeAvailabilityMap(av, 9, n);
                    break;
                case 4:
                    makeAvailabilityMap(av, 1, n);
                    makeAvailabilityMap(av, 7, n);
                    makeAvailabilityMap(av, 5, n);
                    makeAvailabilityMap(av, 6, n);
                    break;
                case 5:
                    makeAvailabilityMap(av, 2, n);
                    makeAvailabilityMap(av, 8, n);
                    makeAvailabilityMap(av, 4, n);
                    makeAvailabilityMap(av, 6, n);
                    break;
                case 6:
                    makeAvailabilityMap(av, 3, n);
                    makeAvailabilityMap(av, 4, n);
                    makeAvailabilityMap(av, 5, n);
                    makeAvailabilityMap(av, 9, n);
                    break;
                case 7:
                    makeAvailabilityMap(av, 1, n);
                    makeAvailabilityMap(av, 4, n);
                    makeAvailabilityMap(av, 8, n);
                    makeAvailabilityMap(av, 9, n);
                    break;
                case 8:
                    makeAvailabilityMap(av, 2, n);
                    makeAvailabilityMap(av, 5, n);
                    makeAvailabilityMap(av, 7, n);
                    makeAvailabilityMap(av, 9, n);
                    break;
                case 9:
                    makeAvailabilityMap(av, 3, n);
                    makeAvailabilityMap(av, 6, n);
                    makeAvailabilityMap(av, 7, n);
                    makeAvailabilityMap(av, 8, n);
                    break;
            }

        }
    }

//    int availablePlaces(boolean av[][],int outerRank,int &innerRank,int rank)
    int availablePlaces(boolean av[][], int outerRank, holder h, int rank)//holder h contains critical:innerRank
    {
        int i, j, r = 0, c = 0;
        holder ht = new holder();
        int sum = 0;
        h.innerRank = 0;
        getCoordinates(outerRank, 1, ht);//changed
        r = ht.r;
        c = ht.c;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (av[r + i][c + j]) {
                    sum++;
                    if (sum == rank) {
                        h.innerRank = i * 3 + (j + 1);
                    }
                }
            }
        }//innerRank contains the last availaible place
        return sum;
    }

    int findFavorableNumber() {
        boolean av[][] = new boolean[9][9];
        int number = 0;
        int j, i = 0, smallestProduct = 100, currentProduct;
        holder h = new holder();
        while (i < 9) {
            if (remaining[i] != 0) {
                if (number == 0) {
                    number = remaining[i];
                }
                makeAvailabilityMap(av, 1, remaining[i]);
                currentProduct = availablePlaces(av, 1, h, 1);
                j = h.innerRank;
                makeAvailabilityMap(av, 5, remaining[i]);
                currentProduct *= availablePlaces(av, 5, h, 1);
                j = h.innerRank;
                makeAvailabilityMap(av, 9, remaining[i]);
                currentProduct *= availablePlaces(av, 9, h, 1);
                j = h.innerRank;
                if (currentProduct < smallestProduct) {
                    number = remaining[i];
                    smallestProduct = currentProduct;
                }
            }
            i++;
        }
        remaining[number - 1] = 0;
        return number;
    }

    void reset(int n) {
        int i, j;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if ((grid[i][j] == n) && (status[i][j] == 1)) {
                    grid[i][j] = 0;
                    status[i][j] = 0;
                }
            }
        }
    }

    int fillObviousPlaces() {
        boolean av[][] = new boolean[9][9], changed;
        int i, j, r, c, innerRank = 0, totalChange = 0;
        holder h = new holder();
        do {
            changed = false;
            for (i = 1; i <= 9; i++) {
                for (j = 1; j <= 9; j++) {
                    makeAvailabilityMap(av, j, i);
                    h.innerRank = innerRank;
                    if (availablePlaces(av, j, h, 1) == 1) {
                        innerRank = h.innerRank;//innerRank contains the last availaible place
                        //getCoordinates(j, innerRank, r, c);//replaced by holder
                        getCoordinates(j, innerRank, h);
                        r = h.r;
                        c = h.c;
                        if (grid[r][c] == 0) {//then fill that place
                            changed = true;
                            setPlace(av, j, innerRank, i, true);
                            status[r][c] = 2;
                            totalChange++;
                        }
                    }
                }
            }
        } while (changed);
        return totalChange;
    }

    void getPossibleRank(int nm[], int pr[], int digit) {
        int i, j, index;
        boolean boxAv[][] = new boolean[3][3];
        for (i = 1; i <= 9; i++) {
            pr[i - 1] = i;//critical change ,please mind.
            boxAv[(i - 1) / 3][(i - 1) % 3] = true;
        }
        switch (digit) {
            case 1:
            case 2:
            case 3:
                //already taken care by the previous loop
                break;
            case 4:
                switch (nm[0]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (nm[2]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                index = 0;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (boxAv[i][j]) {
                            pr[index] = i * 3 + j + 1;
                            index++;
                        }
                    }
                }
                break;
            case 5:
                switch (nm[2]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (nm[0]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                index = 0;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (boxAv[i][j]) {
                            pr[index] = i * 3 + j + 1;
                            index++;
                        }
                    }
                }
                break;
            case 6:
                int t[] = new int[9];
//vertical box 1
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        boxAv[i][j] = true;
                    }
                }
                getPossibleRank(nm, t, 4);
                switch (nm[0]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (t[nm[3] - 1]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }

                switch (nm[1]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                index = 0;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (boxAv[i][j]) {
                            pr[index] = i * 3 + j + 1;
                            index++;
                        }
                    }
                }
//vertical box 2
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        boxAv[i][j] = true;
                    }
                }
                getPossibleRank(nm, t, 5);
                switch (nm[2]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (t[nm[4] - 1]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (nm[1]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                index = 1;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (boxAv[i][j]) {
                            pr[4 + index] = i * 3 + j + 1;
                            index--;
                        }
                    }
                }
//horizontal box 1
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        boxAv[i][j] = true;
                    }
                }
                //getPossibleRank(nm,t,5);//already calculated from vertical box 2
                switch (nm[1]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }

                switch (t[nm[4] - 1]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (nm[0]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                index = 0;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (boxAv[i][j]) {
                            pr[2 + index] = i * 3 + j + 1;
                            index++;
                        }
                    }
                }
//horizontal box 2
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        boxAv[i][j] = true;
                    }
                }
                getPossibleRank(nm, t, 4);
                switch (nm[1]) {
                    case 1:
                    case 2:
                    case 3:
                        boxAv[0][0] = false;
                        boxAv[0][1] = false;
                        boxAv[0][2] = false;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        boxAv[1][0] = false;
                        boxAv[1][1] = false;
                        boxAv[1][2] = false;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        boxAv[2][0] = false;
                        boxAv[2][1] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (t[nm[3] - 1]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                switch (nm[2]) {
                    case 1:
                    case 4:
                    case 7:
                        boxAv[0][0] = false;
                        boxAv[1][0] = false;
                        boxAv[2][0] = false;
                        break;
                    case 2:
                    case 5:
                    case 8:
                        boxAv[0][1] = false;
                        boxAv[1][1] = false;
                        boxAv[2][1] = false;
                        break;
                    case 3:
                    case 6:
                    case 9:
                        boxAv[0][2] = false;
                        boxAv[1][2] = false;
                        boxAv[2][2] = false;
                        break;
                }
                index = 1;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (boxAv[i][j]) {
                            pr[6 + index] = i * 3 + j + 1;
                            index--;
                        }
                    }
                }
                break;
        }
    }

    boolean nextNumberMap(boolean av[][], int nm[], int n, boolean carryFlag) {
        boolean result = true;
        int possibleRank[] = new int[9];
        int i, j, r, c, outerRank, innerRank, t;
        holder h = new holder();
        if (carryFlag) {
            for (i = 0; i < 6; i++) {
                nm[i] = 0;
            }
            i = 0;
        } else {
            i = 5;
        }
        carryFlag = false;//reused here
        while ((i < 6) && (i >= 0)) {
            //nm[i]=0;
            t = nm[i];
            switch (i + 1) {
                case 1:
                case 2:
                case 3:
                    outerRank = i * 3 + (i + 1);
                    if (nm[i] != 0) {
                        setPlace(av, outerRank, nm[i], n, false);
                    }

                    for (j = nm[i] + 1; j <= 9; j++) {
                        getCoordinates(outerRank, j, h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            nm[i] = j;
                            carryFlag = false;
                            setPlace(av, outerRank, j, n, true);
                            break;
                        }
                    }
                    break;
                case 4:
                case 5:
                    outerRank = (i + 1) == 4 ? 3 : 7;
                    getPossibleRank(nm, possibleRank, i + 1);
                    if (nm[i] != 0) {
                        setPlace(av, outerRank, possibleRank[nm[i] - 1], n, false);
                    }

                    for (j = nm[i] + 1; j <= 4; j++) {
                        innerRank = possibleRank[j - 1];
                        getCoordinates(outerRank, innerRank, h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            nm[i] = j;
                            carryFlag = false;
                            setPlace(av, outerRank, innerRank, n, true);
                            break;
                        }
                    }
                    break;
                case 6:
                    getPossibleRank(nm, possibleRank, i + 1);
                    j = nm[i];
                    if (nm[i] != 0) {
                        setPlace(av, 2, possibleRank[(j - 1) / 2], n, false);
                        setPlace(av, 8, possibleRank[((j - 1) / 2) + 4], n, false);
                        setPlace(av, 4, possibleRank[2 + (j - 1) % 2], n, false);
                        setPlace(av, 6, possibleRank[2 + ((j - 1) % 2) + 4], n, false);
                    }
                    for (j = nm[i] + 1; j <= 4; j++) {
                        getCoordinates(2, possibleRank[(j - 1) / 2], h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            getCoordinates(8, possibleRank[((j - 1) / 2) + 4], h);
                            r = h.r;
                            c = h.c;
                            if (av[r][c]) {
                                getCoordinates(4, possibleRank[2 + (j - 1) % 2], h);
                                r = h.r;
                                c = h.c;
                                if (av[r][c]) {
                                    getCoordinates(6, possibleRank[2 + ((j - 1) % 2) + 4], h);
                                    r = h.r;
                                    c = h.c;
                                    if (av[r][c]) {
                                        nm[i] = j;
                                        setPlace(av, 2, possibleRank[(j - 1) / 2], n, true);
                                        setPlace(av, 8, possibleRank[((j - 1) / 2) + 4], n, true);
                                        setPlace(av, 4, possibleRank[2 + (j - 1) % 2], n, true);
                                        setPlace(av, 6, possibleRank[2 + ((j - 1) % 2) + 4], n, true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
            if (t == nm[i]) {
                nm[i] = 0;
                carryFlag = true;
                i--;

            } else {
                i++;
                carryFlag = false;
            }
        }
        if (i == 6) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

//problem generation functions defined
    int nextDigit(int d, int limit) {
        int result = 0;
        result = d + 1;
        if (result > limit) {
            result = 1;
        }
        return result;
    }

    boolean generateSudokuMatrix(int face) {//returns false on deadlock,true otherwise
        int j, k, outerRank = 0, innerRank, r, c, incrementDigit = 0;
        int numberMap[] = new int[6], possibleRank[] = new int[9];
        boolean fitting, av[][] = new boolean[9][9], deadLock, firstBeggining;
        Random rand = new Random();
        holder h = new holder();
        if (face > 9) {
            return true;
        }

//        srand((unsigned) time(0));//randomizing function seeder here

        //make the availaibilty map
        for (j = 1; j <= 9; j++) {
            makeAvailabilityMap(av, j, face);
        }
        //randomize the digits of the numberMap
        for (j = 0; j < 6; j++) {
            if (j < 3) {
                numberMap[j] = rand.nextInt(9) + 1;//(rand() % 9) + 1;//radomize
            } else {
                numberMap[j] = rand.nextInt(4) + 1;//(rand() % 4) + 1;//randomize
            }
        }
        fitting = true;
        //check if the randomized numberMap is fitting in the matrix
        for (j = 1; j <= 6; j++) {
            getPossibleRank(numberMap, possibleRank, j);
            k = numberMap[j - 1];
            incrementDigit = j - 1;//suspicious j'th digit will be incremented if this numberMap
            // is not valid
            if (j != 6) {
                k--;//digits are from 1 till 9(or 4) but indices start from 0
                innerRank = possibleRank[k];
                switch (j) {
                    case 1:
                    case 2:
                    case 3:
                        outerRank = (j - 1) * 3 + j;
                        break;
                    case 4:
                        outerRank = 3;
                        break;
                    case 5:
                        outerRank = 7;
                        break;
                }
                getCoordinates(outerRank, innerRank, h);
                r = h.r;
                c = h.c;
                if (!av[r][c]) {
                    fitting = false;
                    break;
                }
            } else {
                fitting = false;
                getCoordinates(2, possibleRank[(k - 1) / 2], h);
                r = h.r;
                c = h.c;
                if (av[r][c]) {
                    getCoordinates(8, possibleRank[((k - 1) / 2) + 4], h);
                    r = h.r;
                    c = h.c;
                    if (av[r][c]) {
                        getCoordinates(4, possibleRank[2 + (k - 1) % 2], h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            getCoordinates(6, possibleRank[2 + ((k - 1) % 2) + 4], h);
                            r = h.r;
                            c = h.c;
                            if (av[r][c]) {
                                fitting = true;
                            }
                        }
                    }
                }
            }
        }
        firstBeggining = true;
        do {
            if (fitting)//if the number map is possible,then fit in that number map
            {
                implementNumberMap(av, numberMap, face, true);
                fitting = false;
            } else//since the randomized number map is not fitting,find the next possible number map
            {
                if (nextPossibleNumberMap(av, numberMap, incrementDigit, false))//implement the
                //next possible new 'valid' number map
                {
                    implementNumberMap(av, numberMap, face, true);
                } else if (firstBeggining)//find from the beggining
                {
                    nextNumberMap(av, numberMap, face, true);//we need set functions in this
                    //coz it is calculating from the first digit and 'setting' positions
                    firstBeggining = false;
                } else {
                    //same numberMap repeated twice,because of deadLock for the next face
                    //therefore return false for this Face
                    //implementNumberMap(av,numberMap,face,false);
                    return false;
                }
                if (numberMap[0] == 0)//deadlock
                {
                    return false;
                }
            }
            deadLock = !generateSudokuMatrix(face + 1);
            if (deadLock) {
                implementNumberMap(av, numberMap, face, false);
            }
        } while (deadLock);
        return true;
    }

    boolean nextPossibleNumberMap(boolean av[][], int nm[], int incrementDigit, boolean carryFlag) {
        //no set functions
        boolean result = true;
        int possibleRank[] = new int[9];
        int i, j, r = 0, c = 0, outerRank, innerRank, t;
        holder h = new holder();
        if (carryFlag) {
            for (i = 0; i < 6; i++) {
                nm[i] = 0;
            }
            i = 0;
        } else {
            i = incrementDigit;
        }
        carryFlag = false;//reused here
        while ((i < 6) && (i >= 0)) {
            //nm[i]=0;
            t = nm[i];
            switch (i + 1) {
                case 1:
                case 2:
                case 3:
                    outerRank = i * 3 + (i + 1);
                    for (j = nm[i] + 1; j <= 9; j++) {
                        getCoordinates(outerRank, j, h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            nm[i] = j;
                            carryFlag = false;
                            break;
                        }
                    }
                    break;
                case 4:
                case 5:
                    outerRank = (i + 1) == 4 ? 3 : 7;
                    getPossibleRank(nm, possibleRank, i + 1);
                    for (j = nm[i] + 1; j <= 4; j++) {
                        innerRank = possibleRank[j - 1];
                        getCoordinates(outerRank, innerRank, h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            nm[i] = j;
                            carryFlag = false;
                            break;
                        }
                    }
                    break;
                case 6:
                    getPossibleRank(nm, possibleRank, i + 1);
                    j = nm[i];
                    for (j = nm[i] + 1; j <= 4; j++) {
                        getCoordinates(2, possibleRank[(j - 1) / 2], h);
                        r = h.r;
                        c = h.c;
                        if (av[r][c]) {
                            getCoordinates(8, possibleRank[((j - 1) / 2) + 4], h);
                            r = h.r;
                            c = h.c;
                            if (av[r][c]) {
                                getCoordinates(4, possibleRank[2 + (j - 1) % 2], h);
                                r = h.r;
                                c = h.c;
                                if (av[r][c]) {
                                    getCoordinates(6, possibleRank[2 + ((j - 1) % 2) + 4], h);
                                    r = h.r;
                                    c = h.c;
                                    if (av[r][c]) {
                                        nm[i] = j;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
            if (t == nm[i]) {
                nm[i] = 0;
                carryFlag = true;
                i--;

            } else {
                i++;
                carryFlag = false;
            }
        }
        if (i == 6) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    void implementNumberMap(boolean av[][], int numberMap[], int face, boolean setBit) {
        int j, k, outerRank = 0, innerRank, possibleRank[] = new int[9];
        for (j = 1; j <= 6; j++) {
            getPossibleRank(numberMap, possibleRank, j);
            k = numberMap[j - 1];
            if (j != 6) {
                k--;//digits are from 1 till 9(or 4) but indices start from 0
                innerRank = possibleRank[k];
                switch (j) {
                    case 1:
                    case 2:
                    case 3:
                        outerRank = (j - 1) * 3 + j;
                        break;
                    case 4:
                        outerRank = 3;
                        break;
                    case 5:
                        outerRank = 7;
                        break;
                }
                setPlace(av, outerRank, innerRank, face, setBit);
            } else {
                setPlace(av, 2, possibleRank[(k - 1) / 2], face, setBit);
                setPlace(av, 8, possibleRank[((k - 1) / 2) + 4], face, setBit);
                setPlace(av, 4, possibleRank[2 + (k - 1) % 2], face, setBit);
                setPlace(av, 6, possibleRank[2 + ((k - 1) % 2) + 4], face, setBit);
            }
        }
    }

    void makePuzzle(int puzzle[][], int difficulty) {
        Random rand = new Random();
        int hintCount = rand.nextInt(4);
        int i, j;
        int r, c;
        switch (difficulty) {
            case 1://easy
                //goes from 30 to 34;
                hintCount += 30;
                break;
            case 2://moderate
                hintCount += 25;
                break;
            case 3://hard
                hintCount += 20;
                break;
        }
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                puzzle[i][j] = 0;
                grid[i][j] = 0;
                status[i][j] = 0;
            }
        }
        generateSudokuMatrix(1);        
        i = 0;
        while (i < hintCount) {
            j = rand.nextInt(81) + 1;
            r = (j - 1) / 9;
            c = (j - 1) % 9;
            status[r][c] = 2;
            puzzle[r][c] = grid[r][c];
            i++;
        }
    }
}
