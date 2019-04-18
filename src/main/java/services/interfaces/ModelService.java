package services.interfaces;

import model.Model;

public interface ModelService {
    boolean createModel(Model model);
    boolean updateModel(Model newModel);
    Model getModel(String modelId);
    boolean deleteModel(String modelId);
}
