import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ScriptRun extends Menu {
    private static String token;
    private static String id;
    private static int comIdStr ;
    private static int comIdPost;
    private static int comPieces;
    private static String comText;
    private static int comKD;
    private static final String comYourPage = "ваша страница - ";
    private static final String comInfinitely = "бесконечно.";
    private static int captcha;

    public void scriptRun() throws Throwable {
        new Connection();
        Scanner tokenSc = new Scanner(System.in);
        Scanner idSc = new Scanner(System.in);
        do {
            System.out.print(COLOR_PURPLE + "Введи токен страницы: " + COLOR_RED);
            token = tokenSc.nextLine();
            if (token.equals("exit")) {
                System.out.println(COLOR_BLACK + "--------------------------------------");
                Menu menu = new Menu();
                menu.startSelection();
            }
            token = token.replace(" ","");
            if (token.isEmpty()) System.out.println(COLOR_RED + "ERROR: токен не может быть пустым.");
        } while (token.isEmpty());
        do {
            System.out.print(COLOR_PURPLE + "Введи id страницы (только цифры): ");
            id = idSc.nextLine();
            id = id.replace(" ","");
            if (id.isEmpty()) System.out.println(COLOR_RED + "ERROR: id не может быть пустым.");
        } while (id.isEmpty());
        System.out.println(COLOR_CYAN + "Проверка...");
        try {
            connection(token,Integer.parseInt(id));
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "ERROR: Проверь токен и id, попробуй ещё раз");
            System.out.println(COLOR_GREEN + "Или напиши в токен \"" + COLOR_RED + "exit" + COLOR_GREEN + "\" для выхода в главное меню.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            scriptRun();
        }
        for (int i = 3; i > 0; i--) {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 900));
            System.out.println(i + "...");
        }
        String method = vk.users().get(actor).executeAsString();
        if (method.contains("\":" + id + ",\"")) { }
        else {
            System.out.println(COLOR_RED + "ERROR: Проверь токен и id, попробуй ещё раз");
            System.out.println(COLOR_GREEN + "Или напиши в токен \"" + COLOR_RED + "exit" + COLOR_GREEN + "\" для выхода в главное меню.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            scriptRun();
        }
        if (method.contains("error")) {
            System.out.println(COLOR_RED + "ERROR: Проверь токен и id, попробуй ещё раз");
            System.out.println(COLOR_GREEN + "Или напиши в токен \"" + COLOR_RED + "exit" + COLOR_GREEN + "\" для выхода в главное меню.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            scriptRun();
        } else {
            System.out.println(COLOR_YELLOW + "Успешно подключено." + COLOR_BLACK + "\n--------------------------------------------------------------------------");
        }
        start();
    }

    public void start() throws Throwable {
        Scanner comIdStrSc = new Scanner(System.in);
        Scanner comIdPostSc = new Scanner(System.in);
        Scanner comPiecesSc = new Scanner(System.in);
        Scanner comTextSc = new Scanner(System.in);
        Scanner comKDSc = new Scanner(System.in);
        Scanner checkCorrectnessSc = new Scanner(System.in);
        Scanner captchaSc = new Scanner(System.in);
        try {
            System.out.println(COLOR_PURPLE + "Напиши в id страницы \"" + COLOR_RED + "100" + COLOR_PURPLE + "\" для выхода в главное меню.");
            System.out.print(COLOR_GREEN + "Поставь в начале \"-\" и id сообщество(для спама в посте сообщества).\nВведи id страницы/группы где будет проводится спам {0 - твоя страница}: ");
            comIdStr = comIdStrSc.nextInt();
            if (comIdStr == 100) {
                System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                Menu menu = new Menu();
                menu.startSelection();
            }
            System.out.print("Введи id поста: ");
            comIdPost = comIdPostSc.nextInt();
            System.out.print("Введи колличество комментариев {0 - бесконечно}: ");
            comPieces = comPiecesSc.nextInt();
            System.out.print("Введи текст комментария: ");
            comText = comTextSc.nextLine();
            System.out.print("1000мс - 1сек\nВведи задержку в мс {не меньше 500мс, не больше 10000мс}: ");
            comKD = comKDSc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(COLOR_RED + "ERROR: Unknown error.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        }
        /*--------------------------------------------------------------------------------*/
        if (comPieces == 0) {
            comPieces = 2100000000;
        } else if (comPieces < 1 || comPieces > 2000000000) {
            System.out.println(COLOR_RED + "ERROR: Колличество комментариев маленькое или большое.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        } else if (comText.isEmpty()) {
            System.out.println(COLOR_RED + "ERROR: Текст комментария не может быть пустым.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        } else if (comKD < 500 || comKD > 10000) {
            System.out.println(COLOR_RED + "ERROR: Задержка не может быть меньше 500мс, и больше 10000мс.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        }
        /*--------------------------------------------------------------------------------*/
        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
        StringBuilder sb = new StringBuilder(COLOR_BLUE + "Проверьте правильность.");
        if (comIdStr == 0) {
            comIdStr = Integer.parseInt(id);
            sb.append(COLOR_BLUE + "\nid страницы спама: " + COLOR_PURPLE + comYourPage + comIdStr);
        } else {
            sb.append(COLOR_BLUE + "\nid страницы спама: " + COLOR_PURPLE + comIdStr);
        }
        sb.append(COLOR_BLUE + "\nid поста: " + COLOR_PURPLE + comIdPost);
        if (comPieces == 2100000000) {
            sb.append(COLOR_BLUE + "\nКолличество комментариев: " + COLOR_PURPLE + comInfinitely);
        } else {
            sb.append(COLOR_BLUE + "\nКолличество комментариев: " + COLOR_PURPLE + comPieces);
        }
        sb.append(COLOR_BLUE + "\nТекст комментария: " + COLOR_PURPLE + comText);
        sb.append(COLOR_BLUE + "\nЗарержка: " + COLOR_PURPLE + comKD + "мс.");
        sb.append(COLOR_CYAN + "\n1 - все верно\n2 - заполнить заново.");
        System.out.print(sb + "\nВсе правильно?: ");
        int checkCorrectness = checkCorrectnessSc.nextInt();
        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
        if (checkCorrectness == 1) {
            for (int i = 1; i < comPieces + 1; i++) {
                String request = vk.wall().createComment(actor, comIdPost).ownerId(comIdStr).message(comText).executeAsString();
                if (request.contains("error")) {
                    if (request.contains("14") & request.contains("Captcha needed")) {
                        System.out.println(COLOR_RED + "ERROR: Появилась капча.(если капчи очень много вам стоит вручную сделать коммент и ввести капчу, или же подождать определенное кол-во времени)\n1 - Продолжить.\n2 - выход.");
                        System.out.print("Продолжить?: ");
                        captcha = captchaSc.nextInt();
                        if (captcha == 1) {
                            System.out.println("Продолжение...\nAnti-Captcha KD 15 sek.");
                            for (int c = 15; c > 0; c--) {
                                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                                System.out.println(c + "...");
                            }
                        } else {
                            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                            start();
                        }
                        i = i - 1;
                        continue;
                    } else if (request.contains("100") & request.contains("One of the parameters specified was missing or invalid")) {
                        System.out.println(COLOR_RED + "ERROR: Возможные причины:" +
                                "\nНеверная запись." +
                                "\nНеверная страница.");
                    } else if (request.contains("212") & request.contains("Access to post comments denied")) {
                        System.out.println(COLOR_RED + "Доступ к комментариям запрещен.");
                        start();
                    } else if (request.contains("213") & request.contains("Access to status replies denied")) {
                        System.out.println(COLOR_RED + "Нет доступа к комментированию записи.");
                        start();
                    } else if (request.contains("222") & request.contains("Hyperlinks are forbidden")) {
                        System.out.println(COLOR_RED + "Запрещено размещение ссылок в комментариях.");
                        start();
                    } else if (request.contains("223") & request.contains("Too many replies")) {
                        System.out.println(COLOR_RED + "Превышен лимит комментариев на стене.");
                        start();
                    } else if (request.contains("6") & request.contains("Too many requests per second")) {
                        System.out.println(COLOR_RED + "ERROR: Большой поток отправки, увеличитьте задержку.");
                        start();
                    } else if (request.contains("10") & request.contains("Internal server error: parent deleted")) {
                        System.out.println(COLOR_RED + "ERROR: Запись не найдена.");
                        start();
                    } else {
                        System.out.println(COLOR_RED + "ERROR: Unknown error.");
                        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                        start();
                    }
                }
                System.out.println(COLOR_YELLOW + "Комментарий " + COLOR_WHITE + i + COLOR_YELLOW + " отправлен.");
                Thread.sleep(comKD);
            }
            System.out.println(COLOR_BLUE + "Все комментарии были отправлены." + COLOR_BLACK + "\n--------------------------------------------------------------------------");
        }
        start();
    }
}
