from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials
import os
import matplotlib.pyplot as plt

# Authentication
def authenticate():
    os.environ["s_key"] = "25d4ff540123468a865bea771356ba73"
    os.environ["endpoint"] = 'https://ai-oltyan-octavian.cognitiveservices.azure.com/'
    return ComputerVisionClient(os.environ["endpoint"], CognitiveServicesCredentials(os.environ["s_key"]))

computervision_client = authenticate()

def analyze_image(computervision_client, image_path):
    with open(image_path, "rb") as img:
        return computervision_client.analyze_image_in_stream(img, visual_features=[VisualFeatureTypes.tags, VisualFeatureTypes.objects])

def calculate_metrics(real_labels, computed_labels):
    true_positive = sum([1 if (real_labels[i] == 1 and computed_labels[i] == 1) else 0 for i in range(len(real_labels))])
    false_positive = sum([1 if (real_labels[i] == 0 and computed_labels[i] == 1) else 0 for i in range(len(real_labels))])
    true_negative = sum([1 if (real_labels[i] == 0 and computed_labels[i] == 0) else 0 for i in range(len(real_labels))])
    false_negative = sum([1 if (real_labels[i] == 1 and computed_labels[i] == 0) else 0 for i in range(len(real_labels))])
    
    accuracy = (true_positive + true_negative) / (true_positive + true_negative + false_positive + false_negative)
    precision = true_positive / (true_positive + false_positive) if true_positive + false_positive > 0 else 0
    recall = true_positive / (true_positive + false_negative) if true_positive + false_negative > 0 else 0
    f1_score = 2 * precision * recall / (precision + recall) if precision + recall > 0 else 0

    return accuracy, precision, recall, f1_score

def ex1(computervision_client):
    directory = 'images/bikes/'
    real_labels = [1] * 10 + [0] * 10
    computed_labels = []

    for file in os.listdir(directory):
        file_path = os.path.join(directory, file)
        result = analyze_image(computervision_client, file_path)
        
        bike = any(tag.name.lower() == "bicycle" for tag in result.tags)
        computed_labels.append(1 if bike else 0)
    
    accuracy, precision, recall, f1_score = calculate_metrics(real_labels, computed_labels)
    
    print("Performanța algoritmului de clasificare:")
    print("Acuratețe (Accuracy):", accuracy)
    print("Precizie (Precision):", precision)
    print("Recall:", recall)
    print("Clasificare binara, f1 score:", f1_score)

def ex2a(computervision_client):
    directory = 'images/bikes/'
    for file in os.listdir(directory):
        file_path = os.path.join(directory, file)
        result = analyze_image(computervision_client, file_path)
        
        for ob in result.objects:
            if ob.object_property == "bicycle":
                predict = [ob.rectangle.x, ob.rectangle.y, ob.rectangle.x + ob.rectangle.w, ob.rectangle.y + ob.rectangle.h]
                im = plt.imread(file_path)
                fig, ax = plt.subplots()
                ax.imshow(im)
                rect = plt.Rectangle((predict[0], predict[1]), predict[2] - predict[0], predict[3] - predict[1], fill=False, color="green", linewidth=2)
                ax.add_patch(rect)
                plt.show()
                break

if __name__ == "__main__":
    ex1(computervision_client)
    ex2a(computervision_client)
