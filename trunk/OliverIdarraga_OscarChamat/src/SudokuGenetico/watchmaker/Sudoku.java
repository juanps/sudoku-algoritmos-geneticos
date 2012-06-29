package SudokuGenetico.watchmaker;

public final class Sudoku {

    public final Gen[] cells;
    public final Coordenadas c;

    public Sudoku(Gen[] cells, Coordenadas c) {
        this.c = c;
        this.cells = cells;
    }

    public int getValor(int fila, int col) {
        return cells[c.campo(fila, col)].getValue();
    }

    public boolean isInicial(int fila, int col) {
        return cells[c.campo(fila, col)].isInicial();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Gen cell : cells) {
            buffer.append(' ');
            buffer.append(cell.getValue());
            buffer.append('\n');
        }
        return buffer.toString();
    }

    public static final class Gen {

        private int valor;
        public int desde, hasta;
        private boolean inicial;

        /**
         * @param value The value (1 - 9) contained in this cell.
         * @param fixed Whether or not this cell's value is fixed (a 'given').
         */
        public Gen(int desde, int hasta, int valor, boolean inicial) {
            this.valor = valor;
            this.inicial = inicial;
        }

        public int getValue() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

        public boolean isInicial() {
            return inicial;
        }

        public void setValorInicial(int valor) {
            this.valor = valor;
            this.inicial = true;
        }

        @Override
        public String toString() {
            return "Gen{" + "valor=" + valor + ", desde=" + desde + ", hasta=" + hasta + ", inicial=" + inicial + '}';
        }
    }
}
