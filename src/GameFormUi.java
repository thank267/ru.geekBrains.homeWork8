
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import constants.Settings;
import utils.ArrayTools;

public class GameFormUi {

    private final JFrame GAME_FORM = new JFrame("GAME");

    // массив кнопок
    private List<JButton> buttons = new ArrayList<>();

    // список спаренных номеров
    private List<Integer> passedNumbers = new ArrayList<>();

    // текущий спариваемый номер
    private Integer pressedButtonNumber = 0;

    // пара кнопок для проверки
    private List<JButton> pairPressedButton = new ArrayList<>();
    
    private Timer timer;

    public void createGameForm() {

        timer = new Timer(Settings.TIMER_DELAY, e -> {

            // перерисовываем кнопки
            repaintButtons();

            // разблокируем кнопки
            enableButtons(true);

            if (passedNumbers.size() == Settings.BUTTONS_AMOUNT / 2) {
                System.out.println("Игра окончена");
            }
        });
        timer.setRepeats(false);

        GAME_FORM.setLayout(null);
        GAME_FORM.setVisible(true);
        GAME_FORM.setResizable(false);
        GAME_FORM.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GAME_FORM.setBounds(Settings.FORM_POSITION_COORD, Settings.FORM_POSITION_COORD, Settings.FORM_WIDTH,
                Settings.FORM_HEIGHT);

        createButtons();

        GAME_FORM.repaint();

    }

    private void createButtons() {

        ArrayTools.generateArray(Settings.BUTTONS_AMOUNT).forEach(buttonNumber -> {

            JButton gameButton = new JButton();
            gameButton.setName(String.valueOf(buttonNumber));

            gameButton.setBounds(Settings.xCoord, Settings.yCoord, Settings.BUTTON_WIDTH_HEIGHT,
                    Settings.BUTTON_WIDTH_HEIGHT);

                   
            gameButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!pairPressedButton.contains(gameButton)) {
                        if (pressedButtonNumber == Integer.parseInt(gameButton.getName())
                                && !passedNumbers.contains(pressedButtonNumber)) {
                            passedNumbers.add(pressedButtonNumber);
                            pressedButtonNumber = 0;
                        } else {
                            pressedButtonNumber = Integer.parseInt(gameButton.getName());
                        }

                        gameButton.setText(gameButton.getName());

                        pairPressedButton.add(gameButton);

                        if (pairPressedButton.size() == 2) {
                            pressedButtonNumber = 0;

                            // дизаблим для нажатия кнопки чтобы временно показать нажатую пару кнопок
                            //
                            enableButtons(false);

                            //запускаем таймер для показа
                            timer.start();
                        }
                    }

                }

            });

            GAME_FORM.add(gameButton);
            buttons.add(gameButton);

            Settings.xCoord = Settings.xCoord + 110;

            if (Settings.xCoord > 340) {
                Settings.xCoord = 10;
                Settings.yCoord = Settings.yCoord + Settings.FORM_POSITION_COORD + Settings.xCoord;
            }

        });

    }

    private void repaintButtons() {
        pairPressedButton.forEach(button -> {
            if (!passedNumbers.contains(Integer.parseInt(button.getName()))) {
                button.setText("");

            } else {
                button.setText(button.getName());

            }
        });
        pairPressedButton.clear();
    }
    
    private void enableButtons(boolean enableButtons) {
        buttons.forEach(button -> {
            if (passedNumbers.contains(Integer.parseInt(button.getName()))) {
                button.setEnabled(false);
            } else {
                button.setEnabled(enableButtons);
            }
        });
    }

}
