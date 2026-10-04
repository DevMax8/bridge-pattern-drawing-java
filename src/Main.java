import bridge.AsciiRenderer;
import bridge.Circle;
import bridge.RasterRenderer;
import bridge.Renderer;
import bridge.Shape;
import bridge.Square;
import bridge.VectorRenderer;

public class Main {

    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) {
        if (args.length == 1 && "--demo".equals(args[0])) {
            runDemo();
            return;
        }

        System.out.println("Usage: java -cp out Main --demo");
    }

    private static void runDemo() {
        passed = 0;
        total = 0;

        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();
        Renderer ascii = new AsciiRenderer();

        Circle circleVector = new Circle("C1", 2, vector);

        check(
                "T1",
                "Circle + VectorRenderer",
                "VECTOR circle radius=2",
                circleVector.execute()
        );

        Circle circleRaster = new Circle("C2", 2, raster);

        check(
                "T2",
                "Circle + RasterRenderer",
                "RASTER circle radius=2",
                circleRaster.execute()
        );

        Square squareVector = new Square("S1", 3, vector);

        check(
                "T3",
                "Square + VectorRenderer",
                "VECTOR square side=3",
                squareVector.execute()
        );

        Square squareRaster = new Square("S2", 3, raster);

        check(
                "T4",
                "Square + RasterRenderer",
                "RASTER square side=3",
                squareRaster.execute()
        );

        testRuntimeSwitch();

        Circle circleAscii = new Circle("C3", 2, ascii);

        check(
                "T6",
                "Circle + AsciiRenderer",
                "ASCII circle radius=2",
                circleAscii.execute()
        );

        Square squareAscii = new Square("S3", 3, ascii);

        check(
                "T7",
                "Square + AsciiRenderer",
                "ASCII square side=3",
                squareAscii.execute()
        );

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
    }

    private static void testRuntimeSwitch() {
        Circle circle = new Circle(
                "SWITCH-CIRCLE",
                2,
                new VectorRenderer()
        );

        Shape originalReference = circle;

        String originalId = circle.getId();
        int originalRadius = circle.getRadius();

        String before = circle.execute();

        circle.setImplementation(new RasterRenderer());

        Shape afterReference = circle;

        String after = circle.execute();

        boolean sameObject = originalReference == afterReference;

        boolean stateUnchanged =
                originalId.equals(circle.getId())
                        && originalRadius == circle.getRadius();

        boolean resultChangedCorrectly =
                "VECTOR circle radius=2".equals(before)
                        && "RASTER circle radius=2".equals(after);

        boolean success =
                sameObject
                        && stateUnchanged
                        && resultChangedCorrectly;

        total++;

        if (success) {
            passed++;
        }

        System.out.println(
                "T5 "
                        + (success ? "PASS" : "FAIL")
                        + " | Circle: VectorRenderer -> RasterRenderer"
                        + " | sameObject=" + sameObject
                        + " | stateUnchanged=" + stateUnchanged
        );

        System.out.println(
                "   before=" + before
                        + " | after=" + after
        );

        if (!success) {
            System.out.println(
                    "   expected: sameObject=true"
                            + ", stateUnchanged=true"
                            + ", before=VECTOR circle radius=2"
                            + ", after=RASTER circle radius=2"
            );
        }
    }

    private static void check(
            String testId,
            String participants,
            String expected,
            String actual
    ) {
        total++;

        boolean success = expected.equals(actual);

        if (success) {
            passed++;
        }

        System.out.println(
                testId
                        + " "
                        + (success ? "PASS" : "FAIL")
                        + " | "
                        + participants
                        + " | result="
                        + actual
        );

        if (!success) {
            System.out.println(
                    "   expected=" + expected
            );
        }
    }
}