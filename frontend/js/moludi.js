// JavaScript 파일

async function fetchFoods() {
  try {
      const response = await fetch('http://localhost:8081/foods');
      const data = await response.json();
      return data;
  } catch (error) {
      console.error('Error fetching foods:', error);
  }
}

async function renderFoodItems() {
  const foods = await fetchFoods();
  const foodDiv = document.getElementById('food');

  foods.forEach(food => {
      const foodItemDiv = document.createElement('div');
      foodItemDiv.classList.add('food-item');

      const imgBoxDiv = document.createElement('div');
      imgBoxDiv.classList.add('img-box');

      const imgElement = document.createElement('img');
      imgElement.src = food.link;
      imgElement.alt = food.name;

      const foodName = document.createElement('p');
      foodName.classList.add('food-name');
      foodName.textContent = food.name;

      const foodDetails = document.createElement('div');
      foodDetails.classList.add('food-detail');

      const detailLabels = ['Calories', 'Carbohydrate', 'Protein', 'Province', 'Vitamin'];
      const detailValues = [food.calories, food.carbohydrate, food.protein, food.province, food.vitamin];

      detailLabels.forEach((label, index) => {
          const detailLabel = document.createElement('span');
          detailLabel.classList.add('detail-label');
          detailLabel.textContent = label + ': ';

          const detailValue = document.createElement('span');
          detailValue.classList.add('detail-value');
          detailValue.textContent = detailValues[index];

          const detailContainer = document.createElement('div');
          detailContainer.appendChild(detailLabel);
          detailContainer.appendChild(detailValue);

          foodDetails.appendChild(detailContainer);
      });

      imgBoxDiv.appendChild(imgElement);
      imgBoxDiv.appendChild(foodName);
      imgBoxDiv.appendChild(foodDetails);

      foodItemDiv.appendChild(imgBoxDiv);

      foodDiv.appendChild(foodItemDiv);
  });
}

renderFoodItems();
