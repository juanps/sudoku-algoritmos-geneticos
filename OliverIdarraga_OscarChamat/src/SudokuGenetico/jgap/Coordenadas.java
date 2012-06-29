/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuGenetico.jgap;

/**
 *
 * @author civilian
 */
public class Coordenadas {

    static int n, nn;

    public Coordenadas(int n) {
        this.n = n;
        this.nn = n * n;
    }

    public int campo(int i, int j) {
        return i * nn + j;
    }
}
