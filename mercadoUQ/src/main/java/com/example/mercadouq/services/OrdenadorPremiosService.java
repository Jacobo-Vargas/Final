package com.example.mercadouq.services;

import com.example.mercadouq.entities.Premio;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class OrdenadorPremiosService implements Comparator<Premio> {

    @Override
    public int compare(Premio x, Premio y) {
        int priorityX = x.getFactura().getCliente().getPrioridadEnvio();
        int priorityY = y.getFactura().getCliente().getPrioridadEnvio();
        // Comparar las prioridades
        if (priorityX < priorityY) {
            return -1;
        } else if (priorityX > priorityY){
            return 1;
        } else{
            return Long.compare(x.getFactura().getId(), y.getFactura().getId());
        }
    }
}
