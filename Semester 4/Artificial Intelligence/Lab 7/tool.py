import os
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import tensorflow as tf

def load_images(directory):
    images = []
    labels = []
    for dirname, _, filenames in os.walk(directory):
        for filename in filenames:
            label = 0 if 'withoutSepia' in dirname else 1
            img = Image.open(os.path.join(dirname, filename))
            img = img.resize((128, 128))
            img = np.array(img) / 255.0
            images.append(img)
            labels.append(label)
    return np.array(images), np.array(labels)

def load_datasets(base_path):
    train_dir = os.path.join(base_path, 'train')
    valid_dir = os.path.join(base_path, 'valid')
    test_dir = os.path.join(base_path, 'test')
    
    train_images, train_labels = load_images(train_dir)
    valid_images, valid_labels = load_images(valid_dir)
    test_images, test_labels = load_images(test_dir)
    
    return train_images, train_labels, valid_images, valid_labels, test_images, test_labels

def build_model(input_shape):
    model = tf.keras.models.Sequential([
        tf.keras.layers.Flatten(input_shape=input_shape),
        tf.keras.layers.Dense(128, activation='relu'),
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dense(1, activation='sigmoid')
    ])
    model.compile(optimizer='adam',
                  loss='binary_crossentropy',
                  metrics=['accuracy'])
    return model

def plot_training_history(history):
    plt.plot(history.history['loss'], label='Training Loss')
    plt.plot(history.history['val_loss'], label='Validation Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.title('Training and Validation Loss')
    plt.legend()
    plt.show()

# Paths
base_path = 'forTest'

# Load data
train_images, train_labels, valid_images, valid_labels, test_images, test_labels = load_datasets(base_path)

# Build model
input_shape = (128, 128, 3)
model = build_model(input_shape)

# Train model
history = model.fit(train_images, train_labels, epochs=10, validation_data=(valid_images, valid_labels))

# Evaluate model
test_loss, test_acc = model.evaluate(test_images, test_labels)
print('Test accuracy:', test_acc)

# Plot training history
plot_training_history(history)
