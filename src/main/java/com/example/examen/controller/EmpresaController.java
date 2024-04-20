package com.example.examen.controller;

import com.example.examen.dao.EmpresaRepository;
import com.example.examen.entity.EmpresaEntity;
import com.example.examen.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/examen/v1/empresa")
@AllArgsConstructor

@Tag(
        name = "API DE MANTENIMIENTO EMPLEADOS",
        description = "Esta api es porque lo pidio el profesor YO NO"
)

public class EmpresaController {
    private final EmpresaService empresaService;
    private final EmpresaRepository empresaRepository;

    @PostMapping("/crear")
    @Operation(summary = "Crear una nueva empresa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Empresa Creada exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))})
            })
    public ResponseEntity<EmpresaEntity> crear(@RequestBody EmpresaEntity empresaEntity){
        return ResponseEntity.ok(empresaService.crear(empresaEntity));
    }

    @Operation(summary = "Buscar Una Empresa Por ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Empresa Encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))})
        })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<EmpresaEntity>> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.buscarxId(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<EmpresaEntity>> buscarTodos()
    {
        return ResponseEntity.ok(empresaService.buscarAll());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EmpresaEntity> actualizar(@PathVariable Long id, @RequestBody EmpresaEntity personaEntity)
    {
        return ResponseEntity.ok(empresaService.actualizar(id,personaEntity));
    }
    /*@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<EmpresaEntity> eliminar(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.borrar(id));
    }*/

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        EmpresaEntity employee = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

       empresaRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
