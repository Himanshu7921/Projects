Here's the complete documentation for your code. It includes detailed descriptions of each function, parameters, and their purpose.

---

# **Image Processing Project Documentation**

This script provides basic image processing operations, including cropping, grayscale conversion, negative transformation, binary conversion, and histogram generation. It is designed to demonstrate how to manipulate and visualize images using Python libraries like NumPy, PIL, and Matplotlib.

---

## **Modules Used**
- **NumPy**: For numerical operations and array manipulations.
- **Matplotlib**: For plotting and displaying images.
- **PIL (Python Imaging Library)**: For loading and handling images.

---

## **Functions**

### 1. `display_image_cmap_normal(img_array1, title1, img_array2, title2)`
Displays two images side-by-side without applying any color map.

#### **Parameters:**
- `img_array1`: The first image array to display.
- `title1`: Title of the first image.
- `img_array2`: The second image array to display.
- `title2`: Title of the second image.

---

### 2. `display_image_cmap_gray(img_array1, title1, img_array2, title2)`
Displays two images side-by-side, applying a grayscale colormap to the second image.

#### **Parameters:**
- `img_array1`: The first image array to display.
- `title1`: Title of the first image.
- `img_array2`: The second image array to display.
- `title2`: Title of the second image.

---

### 3. `loadImage(image_path)`
Loads an image from the specified path and converts it into a NumPy array.

#### **Parameters:**
- `image_path`: Path to the image file.

#### **Returns:**
- A NumPy array representing the pixel values of the image.

---

### 4. `crop_image(image, x1, y1, x2, y2)`
Crops a section of the image based on given coordinates.

#### **Parameters:**
- `image`: Original image array.
- `x1`, `y1`: Coordinates of the top-left corner of the cropping area.
- `x2`, `y2`: Coordinates of the bottom-right corner of the cropping area.

#### **Returns:**
- Cropped image as a NumPy array.

---

### 5. `grey_scale(image)`
Converts a color image to grayscale using the weighted sum of RGB channels.

#### **Parameters:**
- `image`: Original image array.

#### **Returns:**
- A 2D NumPy array representing the grayscale image.

---

### 6. `negative_image(image)`
Generates the negative of an image by inverting its pixel values.

#### **Parameters:**
- `image`: Original image array.

#### **Returns:**
- A NumPy array of the negative image.

---

### 7. `threshold_image(image, threshold_value)`
Converts a grayscale image into a binary image using the specified threshold value.

#### **Parameters:**
- `image`: Original image array (color or grayscale).
- `threshold_value`: Pixel intensity threshold for binarization.

#### **Returns:**
- A binary image as a NumPy array with pixel values of `0` or `255`.

---

### 8. `get_histogram_grey_scale(image)`
Generates and displays the histogram of a grayscale image.

#### **Parameters:**
- `image`: Original image array.

#### **Returns:**
- None. Displays the histogram plot.

---

### 9. `display_menu()`
Displays the user menu with options for image processing tasks.

#### **Returns:**
- None. Prints the menu.

---

## **Usage**

1. **Run the script.**
2. **Choose an option from the menu:**
   - `1`: Crop Image
   - `2`: Convert to Grayscale
   - `3`: Convert to Negative
   - `4`: Convert to Binary
   - `5`: Generate Histogram of Grayscale Image
3. Follow the prompts to provide inputs (e.g., coordinates for cropping or threshold value for binary conversion).
4. The output will be displayed as side-by-side images or a histogram.

---

## **Example**

### Input:
```text
Welcome to the Image Processing Project! Please choose an option from the menu below:

    Choose an option between 1 to 5:
    1. Crop Image
    2. Convert to Grey Scale
    3. Convert to Negative Image
    4. Convert to Binary Image
    5. Generate Histogram of Grey Scale Image

Enter your choice (1-5): 2
```
