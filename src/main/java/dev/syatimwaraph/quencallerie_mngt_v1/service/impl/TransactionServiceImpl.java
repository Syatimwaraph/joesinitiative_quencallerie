package dev.syatimwaraph.quencallerie_mngt_v1.service.impl;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Transaction;
import dev.syatimwaraph.quencallerie_mngt_v1.model.TransactionDetail;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.TransactionRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.service.ProductService;
import dev.syatimwaraph.quencallerie_mngt_v1.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductService productService;


    @Override
    public void processSale(Transaction transaction) {
        transaction.setTransactionDate(LocalDateTime.now());

        double total = 0;

        for (TransactionDetail transactionDetail : transaction.getDetailList()){
            Product product = transactionDetail.getProduct();

            // decrease stock
            productService.decreaseStock(product, transactionDetail.getQuantity());

            double subTotal = transactionDetail.getQuantity() * transactionDetail.getUnitPrice();
            transactionDetail.setSubTotal(subTotal);
        }

        transaction.setTotal(total);
        transactionRepository.save(transaction);
    }
}
