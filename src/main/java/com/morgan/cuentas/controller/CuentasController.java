package com.morgan.cuentas.controller;

import com.morgan.cuentas.dto.ActualizarSaldoRequest;
import com.morgan.cuentas.entity.Cuenta;
import com.morgan.cuentas.service.ICuentasService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CuentasController {

    ICuentasService cuentasService;

    @PostMapping("/crear")
    public ResponseEntity<Cuenta> createAccount(@Valid @RequestBody Cuenta usuario) {

        try {
            Cuenta c = cuentasService.getCuentaByClabe(usuario.getClabe());

        }catch (Exception e){
            cuentasService.crearCuenta(usuario);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(usuario);
        }
        throw new RuntimeException("No se pudo crear el cuenta, ya hay un valor de clabe asignado");

    }

    @GetMapping("/get")
    public ResponseEntity<List<Cuenta>> getAccounts() {
        List<Cuenta> cuentas = cuentasService.getCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Cuenta> getAccount(@PathVariable("id") Long idUsuario) {
        Cuenta cuenta = cuentasService.getCuentaPorId(idUsuario);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping("/get/usuario/{id}")
    public ResponseEntity<List<Cuenta>> getCuentasByUsuarioID(@PathVariable("id") Long idUsuario) {
        List<Cuenta> cuentas = cuentasService.getCuentasByUsuarioId(idUsuario);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/get/clabe/{id}")
    public ResponseEntity<Cuenta> getCuentasByUsuarioID(@PathVariable("id") String clabe) {
        Cuenta cuenta = cuentasService.getCuentaByClabe(clabe);
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/actualizar-saldo")
    public ResponseEntity<Void> actualizarSaldo(@RequestBody ActualizarSaldoRequest request) {
        cuentasService.actualizarSaldo(request.getId(), request.getSaldo());
        return ResponseEntity.ok().build();
    }
}
