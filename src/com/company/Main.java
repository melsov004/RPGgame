package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 95;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {260, 270, 250, 210, 300, 230, 280, 225}; //0-воин, 1-маг, 2-кинетик, 3-медик, 4-голем, 5-счастливчик, 6-Berserk, 7-Thor
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 12, 20, 0}; //0-воин, 1-маг, 2-кинетик, 3-медик, 4-голем, 5-счастливчик, 6-Berserk, 7-Thor
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medical", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static boolean isMedicHealing() {
        boolean healing = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[3] - bossDamage <= 0) {
                heroesHealth[3] = 0;
                healing = false;
                break;
            } else if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                healing = true;
                heroesHealth[i] = heroesHealth[i] + generateRandomNumber();
                Random random = new Random();
                int name = random.nextInt(heroesAttackType.length);

                System.out.println("Medic вылечил: " + heroesAttackType[name] + " на " + generateRandomNumber() + " единиц жизни");
                break;
            } else if (heroesHealth[i] > 100 && heroesHealth[i] > 0) {
                healing = false;
            } else healing = false;
        }
        return healing;
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(300);
        return number;
    }


    public static void round() {
        roundNumber++;
        System.out.println("----------------------");
        System.out.println("РАУНД №:" + roundNumber);
        changeDefence();
        isMedicHealing();
        if (bossHealth > 0) {
            bossHits();
        }
        heroesHits();
        printStatistics();
        golemHelp();
        shuffle();
        talentOfBerserk();
        thor();
    }

    public static void changeDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Босс выбрал: " + bossDefenceType);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Герои победили!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Босс победил!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("------------------------------");
        System.out.println("Здоровье Босса: " + bossHealth + " [" + bossDamage + "] ");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " здоровье: " + heroesHealth[i] + "[ " + heroesDamage[i] + "]");
        }
        ;
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHits() {
        for (int i = 0; i < heroesDamage.length; i++) {
            ;
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(10) + 2;
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Критический удар: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean golemHelp() {
        boolean helps = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] - bossDamage < 0) {
                heroesHealth[4] = 0;
                helps = false;
                break;
            } else if (heroesHealth[4] <= 0 && heroesHealth[i] <= 0) {
                helps = false;
                break;
            } else if (heroesHealth[4] - (bossDamage / 5) > 0 && heroesHealth[i] < 0) {
                helps = false;
                break;
            } else if (heroesHealth[4] > 0) {
                heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                heroesHealth[i] = heroesHealth[i] + bossDamage / 5;
                helps = true;
                break;
            } else {
                helps = false;
                break;
            }

        }
        return helps;
    }

    public static void shuffle() {
        Random random = new Random();
        int reply = random.nextInt(2);
        if (reply == 0) {
            System.out.println(heroesAttackType[5] + " уклонения не было!");
        }
        if (reply == 1) {
            System.out.println(heroesAttackType[5] + " уклонение было!");
            heroesHealth[5] = heroesHealth[5] + bossDamage - (bossDamage / 5);
        }
    }

    public static void talentOfBerserk(){
        Random random = new Random();
        int reply = random.nextInt(2);
        if (reply == 0) {
            System.out.println(heroesAttackType[6] + " блокировки не было!");
        }
        if (reply == 1) {
            System.out.println(heroesAttackType[6] + " блокировка была!");
            heroesDamage[6] = heroesDamage[6] + (bossDamage / 2);
            heroesHealth[6] = heroesHealth[6] + (bossDamage / 2) * 2;
        }
    }

    public static boolean thor(){
        boolean deafen = true;
        Random random = new Random();
        int reply = random.nextInt(2);
        if (heroesHealth[7]<= 0 && reply ==0){
            bossDamage = 95;
            System.out.println(heroesAttackType[7] + " оглушения не было!");
            deafen = false;
        }
        if (heroesHealth[7]>0 &&reply == 0){
            bossDamage = 95;
            System.out.println(heroesAttackType[7] + " оглушения не было!");
            deafen = false;
        }
        if (heroesHealth[7]>0 && reply == 1){
            bossDamage = 0;
            System.out.println(heroesAttackType[7] + " оглушение было!");
            deafen = true;
        }
        return deafen;
    }
}
/*
Добавить 4-го игрока Medic, у которого есть способность лечить после каждого раунда на N-ное количество единиц здоровья только одного из членов команды,
имеющего здоровье менее 100 единиц. Мертвых героев медик оживлять не может, и лечит он до тех пор пока жив сам.
Медик не участвует в бою, но получает урон от Босса

ДЗ на сообразительность:
Добавить n-го игрока, Golem, который имеет увеличенную жизнь но слабый удар.
Может принимать на себя 1/5 часть урона исходящего от босса по другим игрокам.
Добавить n-го игрока, Lucky, имеет шанс уклонения от ударов босса.
Добавить n-го игрока, Berserk, блокирует часть удара босса по себе и прибавляет заблокированный урон к своему урону и возвращает его боссу
Добавить n-го игрока, Thor, удар по боссу имеет шанс оглушить босса на 1 раунд, вследствие чего босс пропускает 1 раунд и не наносит урон героям.
Дэдлайн 05.04.2021 23:59
 */
