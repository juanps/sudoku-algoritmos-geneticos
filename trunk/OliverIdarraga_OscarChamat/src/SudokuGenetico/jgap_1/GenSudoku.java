/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuGenetico.jgap_1;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.impl.IntegerGene;

/**
 *
 * @author civilian
 */
public class GenSudoku extends IntegerGene implements Gene {

    /**  Boolean deterimining whether this is an immutable Gene. */
    protected boolean inicial = false;
    protected int valorInicial;
//    protected Configuration configuracion;
//    int desde, hasta;
//    Integer valor;

    public GenSudoku(Configuration conf, int desde, int hasta) throws InvalidConfigurationException {
        super(conf, desde, hasta);
//        this.configuracion = conf;
//        this.desde = desde;
//        this.hasta = hasta;
    }

    public GenSudoku(Configuration conf, int desde, int hasta, boolean inicial, int valor) throws InvalidConfigurationException {
        super(conf, desde, hasta);
//        this.configuracion = conf;
//        this.desde = desde;
//        this.hasta = hasta;
        this.inicial = inicial;
        this.valorInicial = valor;
    }

    @Override
    public Gene newGene() {
        try {
//            dbg(configuracion,desde, hasta, inicial);
//            dbg("valor",valor);
//            return new GenSudoku(configuracion, desde, hasta, inicial, valorInicial);
            return new GenSudoku(super.getConfiguration(), super.getLowerBounds(), super.getUpperBounds(), inicial, valorInicial);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(GenSudoku.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setImmutableValue(int valor) {
//        this.valor=valor;
        setAllele(valor);
        this.valorInicial = valor;
        inicial = true;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_numberGenerator) {
        if (!inicial) //	  m_value = new Integer((int)((m_upperBounds-m_lowerBounds)*a_numberGenerator.nextDouble()+m_lowerBounds));
        {
//            valor = (int) ((desde - hasta + 1) * a_numberGenerator.nextDouble() + desde);
            super.setToRandomValue(a_numberGenerator);
        } else {
//            valor = valorInicial;
            setAllele(valorInicial);
            //	  System.out.println(this.getPersistentRepresentation());
        }
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
        // Don't mutate immutable genes.
        if (inicial) {
            return;
        }
        double dx = (super.getLowerBounds() - super.getUpperBounds()) * a_percentage;
        if (getAllele() == null) {
            setAllele(new Integer((int) dx + super.getLowerBounds()));
        } else {
            int newValue = (int) Math.round(intValue() + dx);
            setAllele(new Integer(newValue));
        }
    }

    @Override
    public String toString() {
        return "GenSudoku{" + "inicial=" + inicial + ", valor=" + getAllele()+ ", valorInicial=" + valorInicial + ", desde=" + super.getLowerBounds() + ", hasta=" + super.getUpperBounds() + " } \n";
    }

    private void dbg(Object... o) {
        System.out.println(Arrays.deepToString(o));
    }
}
