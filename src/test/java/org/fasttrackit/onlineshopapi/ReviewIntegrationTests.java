package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.Review;
import org.fasttrackit.onlineshopapi.exception.ResourceNorFoundException;
import org.fasttrackit.onlineshopapi.service.Reviewservice;
import org.fasttrackit.onlineshopapi.steps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.review.CreateReviewRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewIntegrationTests {

	@Autowired
	private Reviewservice reviewservice;

	@Autowired
	private ProductSteps productSteps;

	@Test
	public void testCreateReview_whenValidRequest_thenReturnReview() throws ResourceNorFoundException {
		Product product = productSteps.createProduct();
		CreateReviewRequest reviewRequest = new CreateReviewRequest();
		reviewRequest.setProductId(product.getId());
		reviewRequest.setContent(("super"));

		Review review = reviewservice.createReview(reviewRequest);

		assertThat(review, notNullValue());
		assertThat(review.getId(), greaterThan(0L));
		assertThat(review.getProduct(), notNullValue());
		assertThat(review.getContent(), is(reviewRequest.getContent()));


	}


}
