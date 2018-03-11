/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha;

import java.util.UUID;

/**
 *
 * @author rafae
 */
public class UUIDTest {
        public static void main(String... args) {
        UUID u = UUID.randomUUID();
        System.out.println(u.toString());
        System.out.println(u.version());
        System.out.println(u.variant());
    }
}
