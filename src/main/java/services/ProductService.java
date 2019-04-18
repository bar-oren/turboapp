package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Model;
import model.ProductModel;
import org.springframework.stereotype.Service;
import services.interfaces.ModelService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductService extends AbstractService implements ModelService {
    private static final String FILE_NAME = System.getProperty("user.dir") + "/src/main/resources/db/products.txt";
    private static final String ACCESS_ERROR_MESSAGE = "Error occur while trying to access Products file: ";
    private ObjectMapper mapper;

    private static ProductService instance = null;
    public synchronized static ProductService getInstance() {
        if (instance == null)
            instance = new ProductService();
        return instance;
    }

    private ProductService() {
        mapper = new ObjectMapper();
    }

    @Override
    public boolean createModel(Model model) {
        if (model instanceof ProductModel) {
            ProductModel newModel = (ProductModel) model;
            try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
                AtomicBoolean isIdExist = new AtomicBoolean(false);
                List<String> products = stream.map(this::convertModel)
                        .filter(productModel -> {
                            if (productModel.equals(newModel.getId())) {
                                isIdExist.set(true);
                                appendError("Id of new product is already exist");
                            }
                            return true;
                        })
                        .map(productModel -> getAsRawData(productModel))
                        .collect(Collectors.toList());
                if (isIdExist.get()) {
                    appendError("Product id already exist");
                    return false;
                }
                products.add(getAsRawData(newModel));
                Files.write(Paths.get(FILE_NAME), products);
                return true;
            } catch (Throwable t) {
                appendError(ACCESS_ERROR_MESSAGE + t.getMessage());
                return false;
            }
        } else {
            appendError("Data is not a valid Product model");
            return false;
        }
    }

    @Override
    public boolean updateModel(Model newModel) {
        if (newModel instanceof ProductModel) {
            ProductModel model = (ProductModel) newModel;
            try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
                AtomicBoolean isNotIdExist = new AtomicBoolean(true);
                List<String> products = new ArrayList<>();
                stream.map(this::convertModel)
                        .forEach(productModel -> {
                            if (productModel.getId().equals(model.getId())) {
                                isNotIdExist.set(false);
                                products.add(getAsRawData(model));
                            } else {
                                products.add(getAsRawData(productModel));
                            }
                        });
                if (isNotIdExist.get()) {
                    appendError("User was not found!");
                    return false;
                }
                Files.write(Paths.get(FILE_NAME), products);
                return true;
            } catch (Throwable t) {
                appendError(ACCESS_ERROR_MESSAGE + t.getMessage());
                return false;
            }
        } else {
            appendError("Data is not a valid Product model");
            return false;
        }
    }

    @Override
    public Model getModel(String modelId) {
        Model result = null;
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            result = stream
                    .map(this::convertModel).
                            filter(productModel -> productModel != null && productModel.getId().equals(modelId)).
                            findFirst().get();
        } catch (Throwable t) {
            appendError(ACCESS_ERROR_MESSAGE + t.getMessage());
        }
        if (result == null) {
            appendError("Product id (" + modelId + ") was not found");
        }
        return result;
    }

    @Override
    public boolean deleteModel(String modelId) {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            List<String> products = stream.map(this::convertModel)
                    .filter(productModel -> !productModel.getId().equals(modelId))
                    .map(productModel -> getAsRawData(productModel))
                    .collect(Collectors.toList());
            Files.write(Paths.get(FILE_NAME), products);
            return true;
        } catch (Throwable t) {
            appendError(ACCESS_ERROR_MESSAGE + t.getMessage());
            return false;
        }
    }

    private String getAsRawData(ProductModel productModel) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(productModel);
        } catch (JsonProcessingException e) {
            appendError("Could not get Product (" + productModel.getId() + ") as raw data");
            return null;
        }
    }

    private ProductModel convertModel(String jsonStr) {
        try {
            return mapper.readValue(jsonStr, ProductModel.class);
        } catch (IOException e) {
            appendError("Data is not a valid Product model: " + jsonStr);
            return null;
        }
    }
}
