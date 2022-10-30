package com.nttdata.bootcamp.assignement1.bank_accounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Maneja la informacion del cliente
 * movementType indica el tipo de operacion: 1=deposito, 2=retiro
 */
@Document(collection = "movement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movement {
    @Id
    String id;
    String registerDate;
    MovementType movementType;
    double amount;
    String bankAccountId;
}
