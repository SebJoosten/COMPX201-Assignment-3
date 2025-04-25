//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        StrHashTableCollisions sth = new StrHashTableCollisions();
        System.out.println(sth.isEmpty());
        sth.insert("apple", "a fruit that keeps doctors away");
        sth.insert("banana", "a yellow fruit that monkeys love");
        sth.insert("cat", "a furry animal that purrs");
        sth.insert("dog", "man's best friend");
        sth.insert("elephant", "the largest land animal");
        sth.insert("fish", "an aquatic creature with gills");
        sth.insert("grape", "a small, round fruit often used to make wine");
        sth.insert("honey", "a sweet substance produced by bees");
        sth.insert("ice", "frozen water");
        sth.insert("juice", "a drink made from fruits or vegetables");

        sth.insert("kiwi", "a small, fuzzy fruit with green flesh");
        sth.insert("lemon", "a sour, yellow citrus fruit");
        sth.insert("monkey", "a playful primate");
        sth.insert("notebook", "a book for writing notes");
        sth.insert("orange", "a sweet, round citrus fruit");
        sth.insert("pencil", "a tool for writing or drawing");
        sth.insert("quilt", "a warm blanket made of fabric layers");
        sth.insert("rose", "a fragrant flower, often red or pink");
        sth.insert("sunflower", "a tall, yellow flower that follows the sun");
        sth.insert("applepie", "a classic dessert made with apples and pastry");

        sth.insert("bread", "a staple food made from flour, water, and yeast");
        sth.insert("carrot", "an orange vegetable that grows underground");
        sth.insert("dragonfly", "an insect with long wings that flies quickly");
        sth.insert("elephantseal", "a large sea mammal with a trunk-like nose");
        sth.insert("firefly", "a glowing insect found in warm climates");
        sth.insert("grapefruit", "a citrus fruit that is sour and slightly bitter");
        sth.insert("houseplant", "a plant that is kept indoors");
        sth.insert("icecream", "a sweet frozen dessert");
        sth.insert("jellyfish", "a marine animal with a gelatinous body");
        sth.insert("kangaroo", "a large marsupial native to Australia");



        sth.contains("dog");
        sth.dump();

        DebuggingCollisions sth2 = new DebuggingCollisions();
        sth2.insertTable(sth);

        System.out.println(sth2.contains("dog"));

        sth2.insertRandomLengeth(10000);

        sth2.dump();


        System.out.println(sth2.contains("dog"));
        System.out.println(sth2.contains("NOT dog"));
        System.out.println(sth2.getValues("dog"));



    }


}