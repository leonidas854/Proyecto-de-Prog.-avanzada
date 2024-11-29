import java.util.Arrays;

public class MetodoVogel {

    private static int[][] asignaciones; 
    private static int costoTotal; 

    public static void resolverMetodoVogel(int[] oferta, int[] demanda, int[][] costos) {
        // Balancear oferta y demanda
        BalanceResult balanceResult = balancear(oferta, demanda, costos);
        oferta = balanceResult.oferta;
        demanda = balanceResult.demanda;
        costos = balanceResult.costos;

        int numFilas = oferta.length;
        int numColumnas = demanda.length;
        asignaciones = new int[numFilas][numColumnas];
        for (int[] fila : asignaciones) Arrays.fill(fila, 0);

        costoTotal = 0;

        while (true) {
            
            int[] multaFilas = calcularMulta(costos, true, oferta, demanda);
            int[] multaColumnas = calcularMulta(costos, false, oferta, demanda);

            
            int maxMulta = Integer.MIN_VALUE;
            int indiceFila = -1, indiceColumna = -1;
            boolean esFila = true;

            
            for (int i = 0; i < numFilas; i++) {
                if (oferta[i] > 0 && multaFilas[i] > maxMulta) {
                    maxMulta = multaFilas[i];
                    indiceFila = i;
                    esFila = true;
                }
            }

            
            for (int j = 0; j < numColumnas; j++) {
                if (demanda[j] > 0 && multaColumnas[j] > maxMulta) {
                    maxMulta = multaColumnas[j];
                    indiceColumna = j;
                    esFila = false;
                }
            }

            
            if (esFila) {
                indiceColumna = encontrarIndiceCostoMinimo(costos[indiceFila], demanda);
            } else {
                final int index = indiceColumna;
                indiceFila = encontrarIndiceCostoMinimo(Arrays.stream(costos).mapToInt(c -> c[index]).toArray(), oferta);
            }

           
            int cantidad = Math.min(oferta[indiceFila], demanda[indiceColumna]);
            asignaciones[indiceFila][indiceColumna] = cantidad;
            costoTotal += cantidad * costos[indiceFila][indiceColumna];

            // Actualizar oferta y demanda
            oferta[indiceFila] -= cantidad;
            demanda[indiceColumna] -= cantidad;

            // Verificar si todas las ofertas y demandas han sido asignadas
            if (Arrays.stream(oferta).allMatch(o -> o == 0) && Arrays.stream(demanda).allMatch(d -> d == 0)) {
                break;
            }
        }

        // Mostrar resultados
        System.out.println("Asignaciones:");
        for (int[] fila : asignaciones) {
            System.out.println(Arrays.toString(fila));
        }
    }

    public static int obtenerCostoTotal() {
        return costoTotal;
    }
     
    private static int[] calcularMulta(int[][] costos, boolean esFila, int[] oferta, int[] demanda) {
        int longitud = esFila ? oferta.length : demanda.length;
        int[] multas = new int[longitud];
    
        for (int i = 0; i < longitud; i++) {
            // Ignorar filas o columnas sin oferta o demanda
            if ((esFila && oferta[i] == 0) || (!esFila && demanda[i] == 0)) {
                multas[i] = 0;
                continue;
            }
    
            // Obtener costos válidos manualmente
            int[] costosValidos;
            if (esFila) {
                // Para filas: considerar solo columnas con demanda > 0
                costosValidos = new int[demanda.length];
                int index = 0;
                for (int j = 0; j < demanda.length; j++) {
                    if (demanda[j] > 0) {
                        costosValidos[index++] = costos[i][j];
                    }
                }
                costosValidos = Arrays.copyOf(costosValidos, index); // Redimensionar el arreglo
            } else {
                // Para columnas: considerar solo filas con oferta > 0
                costosValidos = new int[oferta.length];
                int index = 0;
                for (int j = 0; j < oferta.length; j++) {
                    if (oferta[j] > 0) {
                        costosValidos[index++] = costos[j][i];
                    }
                }
                costosValidos = Arrays.copyOf(costosValidos, index); // Redimensionar el arreglo
            }
    
            // Calcular la multa
            if (costosValidos.length >= 2) {
                Arrays.sort(costosValidos);
                multas[i] = costosValidos[1] - costosValidos[0];
            } else if (costosValidos.length == 1) {
                multas[i] = costosValidos[0]; // Si solo hay un costo válido, la multa es ese costo
            } else {
                multas[i] = 0; // Si no hay costos válidos, la multa es 0
            }
        }
    
        return multas;
    }
    
    
    

    private static int encontrarIndiceCostoMinimo(int[] costos, int[] restricciones) {
        int menorCosto = Integer.MAX_VALUE;
        int indiceMinimo = -1;
        for (int i = 0; i < costos.length; i++) {
            if (restricciones[i] > 0 && costos[i] < menorCosto) {
                menorCosto = costos[i];
                indiceMinimo = i;
            }
        }
        return indiceMinimo;
    }

    private static BalanceResult balancear(int[] oferta, int[] demanda, int[][] costos) {
        int sumaOferta = Arrays.stream(oferta).sum();
        int sumaDemanda = Arrays.stream(demanda).sum();

        if (sumaOferta == sumaDemanda) {
            return new BalanceResult(oferta, demanda, costos);
        }

        int numFilas = oferta.length;
        int numColumnas = demanda.length;

        if (sumaOferta > sumaDemanda) {
            int[][] nuevosCostos = new int[numFilas][numColumnas + 1];
            for (int i = 0; i < numFilas; i++) {
                System.arraycopy(costos[i], 0, nuevosCostos[i], 0, numColumnas);
                nuevosCostos[i][numColumnas] = 0; // Costo ficticio
            }
            int[] nuevaDemanda = Arrays.copyOf(demanda, numColumnas + 1);
            nuevaDemanda[numColumnas] = sumaOferta - sumaDemanda;
            return new BalanceResult(oferta, nuevaDemanda, nuevosCostos);
        } else {
            int[][] nuevosCostos = Arrays.copyOf(costos, numFilas + 1);
            nuevosCostos[numFilas] = new int[numColumnas];
            Arrays.fill(nuevosCostos[numFilas], 0); // Costos ficticios

            int[] nuevaOferta = Arrays.copyOf(oferta, numFilas + 1);
            nuevaOferta[numFilas] = sumaDemanda - sumaOferta;

            return new BalanceResult(nuevaOferta, demanda, nuevosCostos);
        }
    }

    private static class BalanceResult {
        int[] oferta;
        int[] demanda;
        int[][] costos;

        BalanceResult(int[] oferta, int[] demanda, int[][] costos) {
            this.oferta = oferta;
            this.demanda = demanda;
            this.costos = costos;
        }
    }
}
