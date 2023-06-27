package com.example.springbootstudy.controllers;

import com.example.springbootstudy.dtos.ProductRecordDto;
import com.example.springbootstudy.models.ProductModel;
import com.example.springbootstudy.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Classe responsável por lidar com requisições HTTP como (GET / POST / PUT / DELETE)
@RestController
public class ProductController {

    // Utilizando ponto de injeção através do @Autowired que será para que tenhamos acesso a todos esses métodos JPA quando houver necessdade.
    @Autowired
    ProductRepository productRepository;

    // Iniciando métodos CRUD (operações básicas de criação, leitura, atualização e exclusão) em um banco de dados.
    // Para MAPEAR os campos vamos utilizar a FEATURE chamda RECORDS
    // Método POST - Irá receber um recurso, fazer uma validação inicial e salvar na base de dados.
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
    //  ResponseEntity é usado para representar a resposta HTTP completa em um controlador RestController do Spring, permitindo especificar o corpo da resposta, o código de status e outros detalhes relevantes antes de enviá-la de volta ao cliente.

    // Esse método vai receber como corpo da solicitação HTTP via post o ProductRecordDto, por isso a anotação @RequestBody.
    // E para que essa validação realmente entre em vigor é necessário incluir a anotação @Valid para que a validação seja feita.
    // "var" é uma funcionalidade onde ao invés de declarar o tipo da instância em ambos os lados, utilizando o "var" dentro de um escopo fechado é possível fazer a definição apenas "do lado direito", poupando código. Seguindo o código, o DTO recebe as informações, faz as validações porém depois de feito é necessário que na base de dados seja salvo um "productModel.

    // Para fazer a conversão de "DTO" para "model" está sendo utilizado um recurso do próprio Spring chamado "BeanUtils.copyPropertie, esse método recebe o que vai ser convertido e o tipo que ele vai ser convertido em si, no caso recebe a instancia "productRecordDto" que vai ser convertido em "productModel".

    // Feito isso o método retornará uma resposta para o cliente informando que o recurso foi criado (ResponseEntity.status(HttpStatus.CREATED), e no corpo dessa resposta(body) é enviado o que foi salvo ((productRepository.save(productModel)))


    // Método GelALL - Irá listar todos os produtos que temos na base
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll(); // busca todos os itens da lista
        if (!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }
    // Explicação código acima: Se a productList não estiver vazia - Passar por cada um dos elementos fazendo a construção do link para cada id e retornando depois a todos os itens da lista.
    // Na última linha do for o linkTo do Hateoas é um método direcionar um link a um objeto específico, no caso do método getOneProduct da classe ProductController  passando o id que recebe para cada um que será interado pelo for.
    // withSelfRel é um método para redirecionar isso para um dos produtos

    // Método GetOne - Irá obter apenas um recurso do que temos na base
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found.");
        }
        productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }

    // Optional - Pode ou não haver um objeto
    // O uso de <Object> indica que o tipo específico do objeto retornado pode variar e não está especificado no momento. Ao usar <Object>, a API é capaz de retornar qualquer tipo de objeto, pois o tipo real do objeto é determinado em tempo de execução.
    // @PathVariable é uma anotação no Spring que extrai valores de variáveis presentes na URL e as associa a parâmetros de um método. Ela permite que você capture informações específicas da URL, como um ID, e as utilize em seu código, por isso o "value" tem que ser o mesmo informado no mapeamendo da URL (que no caso foi id).
    // product0.get() é uma chamada de método que retorna o valor armazenado dentro do objeto product0.

    // Método Put - Atualizar um recurso
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id")UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }
    // Código acima de fácil entendimento ou já explicado linhas acima.

    // Método Delete - deleta um recurso da base da dados.
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id")UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
    // "productRepository.delete(productO.get());" Utilizando o ".delete" do próprio JPA é possível deletar o recurso passando dentro do método qual será a entidade (no caso productO.get).





























}