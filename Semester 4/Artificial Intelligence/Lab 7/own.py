import os
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt

def custom_abs(value):
    if isinstance(value, (int, float)):
        return abs(value)
    elif isinstance(value, np.ndarray):
        return np.abs(value)
    else:
        raise ValueError("Unsupported data type. Please provide an int, float, or numpy array.")

def custom_int64(value):
    if isinstance(value, (int, float)):
        return np.int64(value)
    elif isinstance(value, np.ndarray):
        return value.astype(np.int64)
    else:
        raise ValueError("Unsupported data type. Please provide an int, float, or numpy array.")

def custom_multiply(arr1, arr2):
    if arr1.shape != arr2.shape:
        raise ValueError("Arrays must have the same shape.")
    return np.multiply(arr1, arr2)

def custom_dot_product(a, b):
    if isinstance(a, np.ndarray) and isinstance(b, np.ndarray):
        return np.dot(a, b)
    else:
        raise ValueError("Unsupported data types. Please provide two numpy arrays.")

def custom_sum(arr):
    return np.sum(arr)

def zeros(shape):
    return np.zeros(shape)

class ANN:
    def __init__(self, input_dims, list_of_units_per_layer, learning_rate=0.01):
        self.input_dims = input_dims
        self.list_of_units_per_layer = list_of_units_per_layer
        self.learning_rate = learning_rate
        self.parameters = self.initialize_parameters()

    def initialize_parameters(self):
        parameters = {}
        for i in range(len(self.list_of_units_per_layer)):
            layer_input_dims = self.input_dims if i == 0 else self.list_of_units_per_layer[i - 1]
            w_tmp = np.random.randn(self.list_of_units_per_layer[i], layer_input_dims) * np.sqrt(2 / layer_input_dims)
            b_tmp = zeros((self.list_of_units_per_layer[i], 1))
            parameters[f"W{i + 1}"] = w_tmp
            parameters[f"b{i + 1}"] = b_tmp
        return parameters

    def sigmoid_activ(self, Z):
        return 1 / (1 + np.exp(-Z))

    def relu_activ(self, Z):
        return np.maximum(0, Z)

    def forward_propagation(self, X, activation_list):
        cache = {"A0": X}
        for i in range(1, len(activation_list) + 1):
            Z = custom_dot_product(self.parameters[f"W{i}"], cache[f"A{i - 1}"]) + self.parameters[f"b{i}"]
            if activation_list[i - 1] == "relu":
                A = self.relu_activ(Z)
            elif activation_list[i - 1] == "sigmoid":
                A = self.sigmoid_activ(Z)
            else:
                raise ValueError("Error: Invalid activation function")
            cache[f"Z{i}"] = Z
            cache[f"A{i}"] = A
        return cache

    def cost_function(self, Y, Y_hat, epsilon=1e-7):
        sigmoid_sum = - custom_multiply(Y, np.log(Y_hat + epsilon)) - custom_multiply((1 - Y), np.log(1 - Y_hat + epsilon))
        sigmoid_cost = custom_sum(sigmoid_sum) / Y.shape[1]
        return sigmoid_cost

    def backward_propagation(self, X, Y, cache, list_of_activations):
        m = X.shape[1]
        grads = {}
        dZ = cache[f"A{len(list_of_activations)}"] - Y
        grads[f"dW{len(list_of_activations)}"] = 1 / m * custom_dot_product(dZ, cache[f"A{len(list_of_activations) - 1}"].T)
        grads[f"db{len(list_of_activations)}"] = 1 / m * custom_sum(dZ)
        dA = custom_dot_product(self.parameters[f"W{len(list_of_activations)}"].T, dZ)

        for i in reversed(range(1, len(list_of_activations))):
            dZ = custom_multiply(dA, custom_int64(cache[f"A{i}"] > 0))
            grads[f"dW{i}"] = 1 / m * custom_dot_product(dZ, cache[f"A{i - 1}"].T)
            grads[f"db{i}"] = 1 / m * custom_sum(dZ)
            dA = custom_dot_product(self.parameters[f"W{i}"].T, dZ)
        return grads

    def gradient_descent(self, grads):
        for val in self.parameters.keys():
            self.parameters[val] -= self.learning_rate * grads[f"d{val}"]

    def train(self, mini_batches, dev_x, dev_y, iterations=100):
        list_activations = ["relu"] * (len(self.list_of_units_per_layer) - 1) + ["sigmoid"]
        all_costs = []

        for epoch in range(iterations):
            total_cost = 0
            for X, Y in mini_batches:
                cache = self.forward_propagation(X, list_activations)
                total_cost += self.cost_function(Y, cache[f"A{len(list_activations)}"])
                grads = self.backward_propagation(X, Y, cache, list_activations)
                self.gradient_descent(grads)
            avg_cost = total_cost / len(mini_batches)
            all_costs.append(avg_cost)
            if epoch % 1 == 0:
                print(f"Epoch {epoch}: loss: {avg_cost} - val_accuracy: {self.calculate_acc(dev_x, dev_y)}")
            self.learning_rate *= 0.99
        return all_costs

    def calculate_acc(self, X_input, Y_output):
        cache = self.forward_propagation(X_input, ["relu"] * (len(self.list_of_units_per_layer) - 1) + ["sigmoid"])
        Y_hat = cache[f"A{len(self.list_of_units_per_layer)}"]
        correct = custom_sum((custom_abs(Y_hat - Y_output) < 0.5).astype(int))
        accuracy = correct / Y_output.shape[1] * 100
        return accuracy

def load_images(directory):
    images = []
    labels = []
    for dirname, _, filenames in os.walk(directory):
        for filename in filenames:
            label = 0 if 'withoutSepia' in dirname else 1
            img = Image.open(os.path.join(dirname, filename)).resize((128, 128))
            images.append(np.array(img))
            labels.append(label)
    images = np.array(images)
    labels = np.array(labels).reshape(-1, 1)
    return images, labels

def create_batch(X, Y, mini_batch_size=16):
    m = X.shape[1]
    mini_batches = []
    num_complete_minibatches = m // mini_batch_size
    for k in range(num_complete_minibatches):
        mini_batch_X = X[:, k * mini_batch_size: (k + 1) * mini_batch_size]
        mini_batch_Y = Y[:, k * mini_batch_size: (k + 1) * mini_batch_size]
        mini_batches.append((mini_batch_X, mini_batch_Y))
    if m % mini_batch_size != 0:
        mini_batch_X = X[:, mini_batch_size * num_complete_minibatches:]
        mini_batch_Y = Y[:, mini_batch_size * num_complete_minibatches:]
        mini_batches.append((mini_batch_X, mini_batch_Y))
    return mini_batches

def flatten_images(images):
    return images.reshape(images.shape[0], -1)

def normalize_images(images, mean, std):
    return (images - mean) / std

def train_ann(rate):
    train_dir = os.path.join('dirs', 'train')
    val_dir = os.path.join('dirs', 'valid')
    test_dir = os.path.join('dirs', 'test')

    train_images, train_labels = load_images(train_dir)
    val_images, val_labels = load_images(val_dir)
    test_images, test_labels = load_images(test_dir)

    mean, std = np.mean(train_images), np.std(train_images)
    train_images = normalize_images(train_images, mean, std)
    val_images = normalize_images(val_images, mean, std)
    test_images = normalize_images(test_images, mean, std)

    train_images_flatten = flatten_images(train_images).T
    val_images_flatten = flatten_images(val_images).T
    test_images_flatten = flatten_images(test_images).T

    train_labels = train_labels.T
    val_labels = val_labels.T
    test_labels = test_labels.T

    training_batches = create_batch(train_images_flatten, train_labels)

    neural_net = ANN(input_dims=49152, list_of_units_per_layer=[32, 16, 1], learning_rate=rate)
    costs = neural_net.train(training_batches, val_images_flatten, val_labels, iterations=24)

    print("Accuracy on test set:", neural_net.calculate_acc(test_images_flatten, test_labels))

    plt.plot(costs)
    plt.ylabel('Cost')
    plt.xlabel('Iterations')
    plt.title(f"Learning rate = {rate}")
    plt.show()

if __name__ == "__main__":
    train_ann(0.01)
    # train_ann(0.001)
    # train_ann(0.0001)
