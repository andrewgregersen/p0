public class cat {

    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("Usage: [path], [-l] number of lines default 15");

    }
}
