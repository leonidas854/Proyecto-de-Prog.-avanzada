public class main {
    public static void main(String[] args) {
        int[] oferta = {25, 40, 50};
        int[] demanda = {30, 35, 25};
        int[][] costos = {
            {600, 320, 500},
            {700, 300, 480},
            {700,350, 450}
        };

        MetodoVogel.resolverMetodoVogel(oferta, demanda, costos);
        System.out.println("Costo Total: " + MetodoVogel.obtenerCostoTotal());
    
    }

}
