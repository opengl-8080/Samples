package sample.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("hello")
public class HelloResource {

    @QueryParam("hoge")
    private Hoge hoge;

    @QueryParam("fuga")
    private Fuga fuga;

    @GET
    public String hello() {
        return this.hoge + " : " + this.fuga;
    }

    public static class Hoge {

        private String value;

        public Hoge(String value) {
            System.out.println("Constructor");
            this.value = value;
        }

//        public static Hoge valueOf(String value) {
//            System.out.println("valueOf()");
//            return new Hoge(value);
//        }

        public static Hoge fromString(String value) {
            System.out.println("fromString");
            return new Hoge(value);
        }

        @Override
        public String toString() {
            return "Hoge(" + this.value + ")";
        }
    }
}
