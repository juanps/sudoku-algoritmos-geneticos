// ============================================================================
//   Copyright 2006, 2007 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================
package SudokuGenetico.watchmaker;

import ejemploDescargadoWatchmakerSudoku.Sudoku;
import ejemploDescargadoWatchmakerSudoku.SudokuEvaluator;
import ejemploDescargadoWatchmakerSudoku.SudokuFactory;
import ejemploDescargadoWatchmakerSudoku.SudokuRowMutation;
import ejemploDescargadoWatchmakerSudoku.SudokuVerticalCrossover;
import ejemploDescargadoWatchmakerSudoku.SudokuView;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import org.uncommons.gui.SpringUtilities;
import org.uncommons.gui.SwingBackgroundTask;
import org.uncommons.maths.random.CellularAutomatonRNG;
import org.uncommons.maths.random.DiscreteUniformGenerator;
import org.uncommons.maths.random.PoissonGenerator;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.StandaloneEvolutionEngine;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.swing.AbortControl;
import org.uncommons.watchmaker.swing.NumericParameterControl;

/**
 * @author 
 */
public class WatchmakerSudoku {

    private static final String[] BLANK_PUZZLE = new String[]{".........",
        ".........",
        ".........",
        ".........",
        ".........",
        ".........",
        ".........",
        ".........",
        "........."};
    private static final String[] EASY_PUZZLE = new String[]{"4.5...9.7",
        ".2..9..6.",
        "39.6.7.28",
        "9..3.2..6",
        "7..9.6..3",
        "5..4.8..1",
        "28.1.5.49",
        ".7..3..8.",
        "6.4...3.2"};
    private static final String[] MEDIUM_PUZZLE = new String[]{"....3....",
        ".....6293",
        ".2.9.48..",
        ".754...38",
        "..46.71..",
        "91...547.",
        "..38.9.1.",
        "1567.....",
        "....1...."};
    private static final String[] HARD_PUZZLE = new String[]{"...891...",
        "....5.8..",
        ".....6.2.",
        "5....4..8",
        "49....67.",
        "8.13....5",
        ".6..8..9.",
        "..5.4.2.7",
        "...1.3.8."};
    private static final String[][] PUZZLES = new String[][]{EASY_PUZZLE,
        MEDIUM_PUZZLE,
        HARD_PUZZLE,
        BLANK_PUZZLE};
    
    private final SelectionStrategy<Object> selectionStrategy
        = new TournamentSelection(selectionPressure.getNumberGenerator());
    private final AbortControl abortControl = new AbortControl();

    public WatchmakerSudoku()
    {
//        add(createControls(), BorderLayout.NORTH);
//        add(sudokuView, BorderLayout.CENTER);
//        add(createStatusBar(), BorderLayout.SOUTH);
//        sudokuView.setPuzzle(EASY_PUZZLE);
    }

            protected Sudoku performTask()
            {
                Random rng = new CellularAutomatonRNG();
                List<EvolutionaryOperator<? super Sudoku>> operators
                    = new ArrayList<EvolutionaryOperator<? super Sudoku>>(2);
                // Cross-over rows between parents (so offspring is x rows from parent1 and
                // y rows from parent2).
                operators.add(new SudokuVerticalCrossover());
                // Mutate the order of cells within individual rows.
                operators.add(new SudokuRowMutation(new PoissonGenerator(2, rng),
                                                    new DiscreteUniformGenerator(1, 8, rng)));

                EvolutionaryOperator<Sudoku> pipeline = new EvolutionPipeline<Sudoku>(operators);

                EvolutionEngine<Sudoku> engine = new GenerationalEvolutionEngine<Sudoku> (new SudokuFactory(puzzle),
                                                                                       pipeline,
                                                                                       new SudokuEvaluator(),
                                                                                       selectionStrategy,
                                                                                       rng);
                engine.addEvolutionObserver(new EvolutionLogger());
                return engine.evolve(populationSize,
                                     eliteCount,
                                     new TargetFitness(0, false), // Continue until a perfect solution is found...
                                     abortControl.getTerminationCondition()); // ...or the user aborts.
            



}


    /**
     * Trivial evolution observer for displaying information at the end
     * of each generation.
     */
    private class EvolutionLogger implements EvolutionObserver<Sudoku> {

    public void populationUpdate(PopulationData<Sudoku> data) {
        sudokuView.setSolution(data.getBestCandidate());
        generationsLabel.setText(String.valueOf(data.getGenerationNumber() + 1));
        timeLabel.setText(TIME_FORMAT.format(((double) data.getElapsedTime()) / 1000));
    }
}
}
